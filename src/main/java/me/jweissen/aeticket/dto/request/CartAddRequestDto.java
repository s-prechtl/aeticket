package me.jweissen.aeticket.dto.request;

import java.util.List;

public record CartAddRequestDto(Long id, List<CartEntryRequestDto> cartEntries) {
}
