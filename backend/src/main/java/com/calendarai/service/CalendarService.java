package com.calendarai.service;

import com.calendarai.dto.EventDto;
import com.calendarai.entity.EventEntity;
import com.calendarai.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private final EventRepository eventRepository;

    public CalendarService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDto createEventInSystem(EventDto dto, String googleAccessToken) throws Exception {
        EventEntity e = new EventEntity();
        e.setTitle(dto.getTitle());
        if (dto.getDate() != null) e.setDate(java.time.LocalDate.parse(dto.getDate()));
        if (dto.getTime() != null) e.setTime(java.time.LocalTime.parse(dto.getTime()));
        e.setLocation(dto.getLocation());
        EventEntity saved = eventRepository.save(e);
        dto.setId(saved.getId());
        dto.setGoogleEventId(saved.getGoogleEventId());
        return dto;
    }

    public EventDto updateEventInSystem(Long id, EventDto dto, String googleAccessToken) {
        Optional<EventEntity> opt = eventRepository.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Event not found");
        EventEntity e = opt.get();
        e.setTitle(dto.getTitle());
        if (dto.getDate() != null) e.setDate(java.time.LocalDate.parse(dto.getDate()));
        if (dto.getTime() != null) e.setTime(java.time.LocalTime.parse(dto.getTime()));
        e.setLocation(dto.getLocation());
        eventRepository.save(e);
        return dto;
    }

    public void deleteEventInSystem(Long id, String googleAccessToken) {
        eventRepository.deleteById(id);
    }

    public List<EventDto> listEvents() {
        return eventRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private EventDto toDto(EventEntity e) {
        EventDto dto = new EventDto();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        if (e.getDate() != null) dto.setDate(e.getDate().toString());
        if (e.getTime() != null) dto.setTime(e.getTime().toString());
        dto.setLocation(e.getLocation());
        dto.setGoogleEventId(e.getGoogleEventId());
        return dto;
    }
}
