package com.calendarai.controller;

import com.calendarai.dto.UploadResponse;
import com.calendarai.entity.Document;
import com.calendarai.service.DocumentService;
import com.calendarai.service.ExtractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UploadController {

    private final DocumentService documentService;
    private final ExtractionService extractionService;

    public UploadController(DocumentService documentService, ExtractionService extractionService) {
        this.documentService = documentService;
        this.extractionService = extractionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {
        Document doc = documentService.saveFile(file);
        UploadResponse response = new UploadResponse();
        response.setDocumentId(doc.getId());
        response.setFilename(doc.getFilename());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/extract")
    public ResponseEntity<?> extract(@RequestParam("documentId") Long documentId) throws Exception {
        var events = extractionService.extractEvents(documentId);
        return ResponseEntity.ok(events);
    }
}
