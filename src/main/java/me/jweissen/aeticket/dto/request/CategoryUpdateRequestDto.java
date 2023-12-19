package me.jweissen.aeticket.dto.request;

public record CategoryUpdateRequestDto(Long id, String name, Double price, Integer stock) {
}
