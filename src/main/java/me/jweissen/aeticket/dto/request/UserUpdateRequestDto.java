package me.jweissen.aeticket.dto.request;

public record UserUpdateRequestDto(Long id, String firstname, String lastname, String email) {
}
