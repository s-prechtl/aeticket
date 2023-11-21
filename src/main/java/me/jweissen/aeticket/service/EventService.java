package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.EventRequestDto;
import me.jweissen.aeticket.dto.request.EventUpdateRequestDto;
import me.jweissen.aeticket.dto.response.EventResponseDto;
import me.jweissen.aeticket.model.Event;
import me.jweissen.aeticket.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public static Event fromDto(EventRequestDto dto) {
        return Event.builder()
            .title(dto.name())
            .description(dto.description())
            .start(dto.from())
            .end(dto.to())
            .build();
    }

    public static EventResponseDto toDto(Event event) {
        return new EventResponseDto(
            event.getId(),
            event.getTitle(),
            event.getStart(),
            event.getEnd(),
            event.getDescription(),
            event.getCategories()
        )
    }

    public void create(EventRequestDto event) {
        eventRepository.save(EventService.fromDto(event));
    }

    public void update(EventUpdateRequestDto event) {
    }

    public void delete(Integer id) {
        eventRepository.deleteById(id);
    }

    public EventResponseDto getById(Integer id) {
        return eventRepository.getReferenceById(id);
    }
}
