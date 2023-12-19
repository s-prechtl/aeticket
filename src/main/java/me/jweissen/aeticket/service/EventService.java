package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.EventRequestDto;
import me.jweissen.aeticket.dto.request.EventUpdateRequestDto;
import me.jweissen.aeticket.dto.response.EventResponseDto;
import me.jweissen.aeticket.model.Category;
import me.jweissen.aeticket.model.Event;
import me.jweissen.aeticket.repository.CategoryRepository;
import me.jweissen.aeticket.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository,
                        CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    public static Event fromDto(EventRequestDto dto) {
        return new Event(
            dto.name(),
            dto.description(),
            dto.from(),
            dto.to()
        );
    }

    public static EventResponseDto toDto(Event event) {
        return new EventResponseDto(
            event.getId(),
            event.getName(),
            event.getStart(),
            event.getEnd(),
            event.getDescription(),
            CategoryService.toDtos(event.getCategories())
        );
    }

    public static List<EventResponseDto> toDtos(List<Event> events) {
        return events.stream().map(EventService::toDto).toList();
    }

    public void create(EventRequestDto dto) {
        Event event = EventService.fromDto(dto);
        event = eventRepository.save(event);
        Event finalEvent = event;
        List<Category> categories = CategoryService.fromDtos(dto.ticketCategories());
        categories.forEach(c -> c.setEvent(finalEvent));
        categoryRepository.saveAll(categories);
    }

    public boolean update(EventUpdateRequestDto dto) {
        return eventRepository.findById(dto.id())
            .map(event -> {
                event.setName(dto.name());
                event.setDescription(dto.description());
                event.setStart(dto.from());
                event.setEnd(dto.to());
                eventRepository.save(event);
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
