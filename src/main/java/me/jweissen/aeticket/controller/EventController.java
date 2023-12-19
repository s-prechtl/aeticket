package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.dto.request.EventRequestDto;
import me.jweissen.aeticket.dto.request.EventUpdateRequestDto;
import me.jweissen.aeticket.dto.response.EventResponseDto;
import me.jweissen.aeticket.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody EventRequestDto event) {
        // TODO admin only
        eventService.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody EventUpdateRequestDto event) {
        // TODO admin only
        eventService.update(event);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO admin only
        eventService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return eventService.getById(id)
            .map(eventResponseDto -> new ResponseEntity<>(eventResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventResponseDto>> getAllFuture() {
        return new ResponseEntity<>(eventService.getAllFuture(), HttpStatus.OK);
    }

}
