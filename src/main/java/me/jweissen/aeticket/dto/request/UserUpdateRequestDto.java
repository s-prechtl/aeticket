package me.jweissen.aeticket.dto.request;

public record UserUpdateRequestDto(Integer id, String firstname, String lastname, String email) {
}
