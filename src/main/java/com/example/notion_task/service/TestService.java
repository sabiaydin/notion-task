package com.example.notion_task.service;

import com.example.notion_task.model.Test;
import com.example.notion_task.notion.model.Page;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TestService {
    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    public static Test mapPageToTest(Page page) {
        // Extracting Title
        JsonNode titleNode = page.getProperties().get("Title");
        String title = extractText(titleNode, "title");

        // Extracting Start Date
        JsonNode startDateNode = page.getProperties().get("StartDate");
        LocalDateTime startDate = extractDate(startDateNode);

        // Extracting End Date
        JsonNode endDateNode = page.getProperties().get("EndDate");
        LocalDateTime endDate = extractDate(endDateNode);

        // Extracting URL
        JsonNode urlNode = page.getProperties().get("URL");
        String url = extractUrl(urlNode);

        // Extracting Recording
        JsonNode recordingNode = page.getProperties().get("Recording");
        String recording = extractUrl(recordingNode);

        return new Test(
                page.getId(),
                title,
                startDate,
                endDate,
                url,
                recording
        );
    }

    private static String extractText(JsonNode node, String arrayKey) {
        if (node != null) {
            if (arrayKey != null && node.has(arrayKey) && node.get(arrayKey).isArray() && !node.get(arrayKey).isEmpty()) {
                return node.get(arrayKey).get(0).get("text").get("content").asText();
            } else if (arrayKey == null) {
                return node.asText();
            }
        }
        return "Unknown";
    }

    private static String extractUrl(JsonNode node) {
        if (node != null && node.has("url")) {
            return node.get("url").asText();
        }
        return "Unknown";
    }

    private static LocalDateTime extractDate(JsonNode node) {
        if (node != null && node.has("date") && node.get("date").has("start")) {
            String dateStr = node.get("date").get("start").asText();
            try {
                if (dateStr.length() == 10) {  // Check if the date is in the format YYYY-MM-DD
                    return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
                } else {
                    return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                }
            } catch (Exception e) {
                log.error("Error parsing date for node: {}", node, e);
            }
        }
        return null;
    }

}
