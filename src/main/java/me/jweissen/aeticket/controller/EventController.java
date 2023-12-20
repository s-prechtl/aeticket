package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.aspect.AdminOnly;
import me.jweissen.aeticket.aspect.UserOnly;
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
    @AdminOnly
    public ResponseEntity<Void> create(@RequestBody EventRequestDto event) {
        eventService.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    @AdminOnly
    public ResponseEntity<Void> update(@RequestBody EventUpdateRequestDto event) {
        eventService.update(event);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    @AdminOnly
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @UserOnly
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return eventService.getById(id)
            .map(eventResponseDto -> new ResponseEntity<>(eventResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/list")
    @UserOnly
    public ResponseEntity<List<EventResponseDto>> getAllFuture() {
        return new ResponseEntity<>(eventService.getAllFuture(), HttpStatus.OK);
    }

}
