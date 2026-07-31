package com.calendarai.service;

import com.calendarai.entity.Document;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
public class OCRService {

    public String extractText(Document doc) throws Exception {
        // Try using Tika for text extraction from PDFs or images.
        Tika tika = new Tika();
        String text = tika.parseToString(new java.io.ByteArrayInputStream(doc.getData()));
        // For complex images, a Tesseract integration could be added here.
        return text;
    }
}
