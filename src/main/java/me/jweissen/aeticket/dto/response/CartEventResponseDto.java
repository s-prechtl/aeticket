package me.jweissen.aeticket.dto.response;

import java.util.List;

public record CartEventResponseDto(
        int id,
        String name,
        String from,
        String to,
        String description,
        List<TicketResponseDto> tickets) {
}