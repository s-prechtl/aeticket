package me.jweissen.aeticket.dto.response;

import java.util.Date;
import java.util.List;

public record EventResponseDto(
        int id,
        String name,
        Date from,
        Date to,
        String description,
        List<CategoryResponseDto> ticketCategories) {
}
