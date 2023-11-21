package me.jweissen.aeticket.dto.request;

import java.util.Date;
import java.util.List;

public record EventRequestDto(
        String name,
        Date from,
        Date to,
        String description,
        List<CategoryRequestDto> ticketCategories) {
}
