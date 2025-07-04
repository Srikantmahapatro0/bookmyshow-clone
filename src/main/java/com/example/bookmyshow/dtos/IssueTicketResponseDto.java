package com.example.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueTicketResponseDto {
    private Long bookingId;
    private int amount;
    private List<Long> showSeatIds;
    private ResponseStatus ResponseStatus;
}
