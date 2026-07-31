# CalendarAI Backend

Spring Boot 3 backend for CalendarAI.

Build:

```bash
mvn package
```

Run with Docker Compose:

```bash
docker compose up --build
```

Environment variables:
- `OPENAI_API_KEY` - OpenAI API key
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` - DB creds

APIs:
- POST /api/upload (multipart file)
- POST /api/extract (documentId)
- GET /api/history
- POST /api/calendar/create
- PUT /api/calendar/update/{id}
- DELETE /api/calendar/delete/{id}

Notes:
- This backend includes simple AI/OCR stubs. Replace `AIService` with a real OpenAI integration for production.
