package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.IssueTicketRequestDto;
import com.scaler.bookmyshow.dtos.IssueTicketResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    public IssueTicketResponseDto issueTicket(IssueTicketRequestDto requestDto) {
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
        } catch (Exception ex) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}