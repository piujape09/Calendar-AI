package com.calendarai.service;

import com.calendarai.entity.Document;
import com.calendarai.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveFile(MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/pdf") && !contentType.startsWith("image/"))) {
            throw new IllegalArgumentException("Only PDF and image files are supported");
        }

        Document d = new Document();
        d.setFilename(file.getOriginalFilename());
        d.setContentType(contentType);
        d.setSize(file.getSize());
        d.setData(file.getBytes());
        d.setEventCount(0);
        return documentRepository.save(d);
    }
}
