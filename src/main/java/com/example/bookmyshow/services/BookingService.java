package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculatorService priceCalculatorService;
    private BookingRepository bookingRepository;

    public BookingService(
            UserRepository userRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            PriceCalculatorService priceCalculatorService,
            BookingRepository bookingRepository
    ) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculatorService = priceCalculatorService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking issueTicket(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException {
        /*----- TAKE LOCK HERE TODAY -------
         * 1. Get the user details from the DB
         * 2. Get the show details
         * --------- Take Lock ------------
         * 3. Get the show seats from the DB
         * 4. Check if the seats are available or not
         * 5. If not, throw an error
         * 6. If yes, mark the status of the seats as LOCKED and udpate the lockedAt
         * ---------- END LOCK ------------------
         * 7. Create booking object
         * 8. Return to controller
         * ----- END LOCK HERE TODAY ---------
         * */

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()) {
            throw new RuntimeException(); // TODO: Add ShowNotFoundException
        }

        Show show = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for(ShowSeat showSeat: showSeats) {
            // TODO: Update condition to include lockedAt as well
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes() > 15))) {
                throw new RuntimeException();
            }
        }

        for(ShowSeat showSeat: showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
            showSeatRepository.save(showSeat);
        }

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setShowSeats(showSeats);
        booking.setShow(show);
        booking.setAmount(priceCalculatorService.calculatePrice(showSeats, show));

        return bookingRepository.save(booking);
    }
}