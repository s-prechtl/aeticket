package me.jweissen.aeticket.dto.request;

public record CategoryRequestDto(String name, Double price, Integer stock, Long eventId) {
}
