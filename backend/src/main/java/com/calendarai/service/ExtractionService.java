package com.calendarai.service;

import com.calendarai.dto.EventDto;
import com.calendarai.entity.Document;
import com.calendarai.entity.EventEntity;
import com.calendarai.repository.DocumentRepository;
import com.calendarai.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExtractionService {

    private final DocumentRepository documentRepository;
    private final EventRepository eventRepository;
    private final OCRService ocrService;
    private final AIService aiService;

    public ExtractionService(DocumentRepository documentRepository, EventRepository eventRepository, OCRService ocrService, AIService aiService) {
        this.documentRepository = documentRepository;
        this.eventRepository = eventRepository;
        this.ocrService = ocrService;
        this.aiService = aiService;
    }

    public List<EventDto> extractEvents(Long documentId) throws Exception {
        Document doc = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        String text = ocrService.extractText(doc);
        List<EventDto> dtos = aiService.extractEventsFromText(text);

        // persist events
        List<EventEntity> entities = new ArrayList<>();
        for (EventDto dto : dtos) {
            EventEntity e = new EventEntity();
            e.setTitle(dto.getTitle());
            e.setLocation(dto.getLocation());
            if (dto.getDate() != null) e.setDate(LocalDate.parse(dto.getDate()));
            if (dto.getTime() != null) e.setTime(LocalTime.parse(dto.getTime()));
            e.setDocument(doc);
            entities.add(eventRepository.save(e));
        }
        doc.setEventCount(entities.size());
        documentRepository.save(doc);

        return dtos;
    }
}
