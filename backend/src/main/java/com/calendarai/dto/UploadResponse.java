package com.calendarai.dto;

public class UploadResponse {
    private Long documentId;
    private String filename;

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
}
