package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.EventRequestDto;
import me.jweissen.aeticket.dto.request.EventUpdateRequestDto;
import me.jweissen.aeticket.dto.response.EventResponseDto;
import me.jweissen.aeticket.model.Event;
import me.jweissen.aeticket.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            CategoryService.toDtos(event.getCategories())
        );
    }

    public static List<EventResponseDto> toDtos(List<Event> events) {
        return events.stream().map(EventService::toDto).toList();
    }

    public void create(EventRequestDto event) {
        eventRepository.save(EventService.fromDto(event));
    }

    public boolean update(EventUpdateRequestDto dto) {
        return eventRepository.findById(dto.id())
            .map(event -> {
                // dto auf object assignen
                return true;
            })
            .orElse(false);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public Optional<EventResponseDto> getById(Long id) {
        return eventRepository.findById(id).map(EventService::toDto);
    }

    public List<EventResponseDto> getAllFuture() {
        return EventService.toDtos(eventRepository.findAllFuture());
    }
}
