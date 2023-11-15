package me.jweissen.aeticket.dto.request;

import java.util.List;

public record EventResponseDto(
        int id,
        String name,
        String from,
        String to,
        String description,
        List<TicketCategoryResponseDto> ticketCategories) {
}
