package me.jweissen.aeticket.dto.request;

import java.util.Date;

// no ticketCategories to prevent undefined behaviour
public record EventUpdateRequestDto(Long id, String name, Date from, Date to, String description) {
}
