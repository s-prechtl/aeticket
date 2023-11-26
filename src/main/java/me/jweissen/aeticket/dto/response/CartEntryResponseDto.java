package me.jweissen.aeticket.dto.response;

public record CartEntryResponseDto(Long id, String name, Double price, Integer amount) {
}
