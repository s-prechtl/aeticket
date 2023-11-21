package me.jweissen.aeticket.dto.request;

public record UserRequestDto(String email, String password, String firstname, String lastname) {
}
