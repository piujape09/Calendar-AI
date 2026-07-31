package com.calendarai.controller;

import com.calendarai.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/calendar")
public class CalendarController {

    private final com.calendarai.service.CalendarService calendarService;

    public CalendarController(com.calendarai.service.CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventDto dto, @RequestHeader(value = "Authorization", required = false) String auth) throws Exception {
        String token = auth != null && auth.startsWith("Bearer ") ? auth.substring(7) : null;
        var created = calendarService.createEventInSystem(dto, token);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDto dto, @RequestHeader(value = "Authorization", required = false) String auth) {
        String token = auth != null && auth.startsWith("Bearer ") ? auth.substring(7) : null;
        var updated = calendarService.updateEventInSystem(id, dto, token);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String auth) {
        String token = auth != null && auth.startsWith("Bearer ") ? auth.substring(7) : null;
        calendarService.deleteEventInSystem(id, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventDto>> list() {
        return ResponseEntity.ok(calendarService.listEvents());
    }
}
