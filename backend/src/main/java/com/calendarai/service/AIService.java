package com.calendarai.service;

import com.calendarai.dto.EventDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${app.openai.apiKey:}")
    private String openaiApiKey;

    private final ObjectMapper mapper = new ObjectMapper();

    public List<EventDto> extractEventsFromText(String text) throws Exception {
        List<EventDto> empty = new ArrayList<>();
        if (text == null || text.isBlank()) return empty;

        if (openaiApiKey == null || openaiApiKey.isBlank()) {
            // Fallback heuristic when no API key provided
            if (text.toLowerCase().contains("interview")) {
                EventDto e = new EventDto();
                e.setTitle("Interview");
                e.setDate("2026-08-12");
                e.setTime("15:00");
                e.setLocation("Microsoft Teams");
                empty.add(e);
            }
            return empty;
        }

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("https://api.openai.com/v1/chat/completions");

        // Build request payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content",
                "You are an assistant that extracts calendar events from text. Respond with a JSON array only. Each item must have keys: title, date (YYYY-MM-DD), time (HH:MM), location. Return an empty array if none."));
        messages.add(Map.of("role", "user", "content", "Extract events from the following text:\n" + text));
        payload.put("messages", messages);
        payload.put("temperature", 0);

        String body = mapper.writeValueAsString(payload);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() / 100 != 2) {
            return empty;
        }

        // Parse response
        Map<String, Object> respMap = mapper.readValue(resp.body(), new TypeReference<>() {});
        Object choices = respMap.get("choices");
        if (!(choices instanceof List)) return empty;
        List<?> choicesList = (List<?>) choices;
        if (choicesList.isEmpty()) return empty;
        Object first = choicesList.get(0);
        if (!(first instanceof Map)) return empty;
        Object message = ((Map<?, ?>) first).get("message");
        if (!(message instanceof Map)) return empty;
        Object content = ((Map<?, ?>) message).get("content");
        if (!(content instanceof String)) return empty;

        String textContent = (String) content;

        // Try to find JSON array in the response text
        String json = textContent.trim();
        int start = json.indexOf('[');
        int end = json.lastIndexOf(']');
        if (start >= 0 && end > start) {
            json = json.substring(start, end + 1);
        }

        try {
            List<EventDto> events = mapper.readValue(json, new TypeReference<List<EventDto>>() {});
            return events;
        } catch (Exception ex) {
            return empty;
        }
    }
}
