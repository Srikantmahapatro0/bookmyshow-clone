package com.example.bookmyshow.dtos;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.util.List;

@Getter
@Setter
public class IssueTicketRequestDto {
    private Long userId;
    private Long showId;
    private List<Long> showSeatIds;
}