package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.IssueTicketRequestDto;
import com.example.bookmyshow.dtos.IssueTicketResponseDto;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/issue")
    public IssueTicketResponseDto issueTicket(@RequestBody IssueTicketRequestDto requestDto) {
        IssueTicketResponseDto responseDto = new IssueTicketResponseDto();
        Booking booking = null;

        try {
            booking = bookingService.issueTicket(
                    requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatIds()
            );
            responseDto.setBookingId(booking.getId());
            responseDto.setAmount(booking.getAmount());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
