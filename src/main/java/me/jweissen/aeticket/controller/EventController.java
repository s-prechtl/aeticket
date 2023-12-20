package me.jweissen.aeticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Create a new event"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @PostMapping("/create")
    @AdminOnly
    public ResponseEntity<Void> create(@RequestBody EventRequestDto event) {
        eventService.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Update an event"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @PutMapping("/update")
    @AdminOnly
    public ResponseEntity<Void> update(@RequestBody EventUpdateRequestDto event) {
        eventService.update(event);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Delete an event"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @DeleteMapping("/delete/{id}")
    @AdminOnly
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Load an event by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = EventResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
    })
    @GetMapping("/{id}")
    @UserOnly
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return eventService.getById(id)
            .map(eventResponseDto -> new ResponseEntity<>(eventResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "List all future events"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventResponseDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            )
    })
    @GetMapping("/list")
    @UserOnly
    public ResponseEntity<List<EventResponseDto>> getAllFuture() {
        return new ResponseEntity<>(eventService.getAllFuture(), HttpStatus.OK);
    }

}
