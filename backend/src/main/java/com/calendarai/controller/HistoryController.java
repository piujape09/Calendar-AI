package com.calendarai.controller;

import com.calendarai.entity.Document;
import com.calendarai.repository.DocumentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/history")
public class HistoryController {
    private final DocumentRepository documentRepository;

    public HistoryController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<?> docs = documentRepository.findAll().stream().map(d -> {
            return java.util.Map.of(
                    "id", d.getId(),
                    "filename", d.getFilename(),
                    "uploadedAt", d.getUploadedAt(),
                    "eventCount", d.getEventCount()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(docs);
    }
}
