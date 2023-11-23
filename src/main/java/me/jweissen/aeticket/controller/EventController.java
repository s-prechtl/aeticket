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

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(eventService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventResponseDto>> getAllFuture() {
        return new ResponseEntity<>(eventService.getAllFuture(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(@RequestBody EventRequestDto event) {
        // TODO admin only
        eventService.create(event);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody EventUpdateRequestDto event) {
        // TODO admin only
        // eventService.update(event);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // TODO admin only
        eventService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
