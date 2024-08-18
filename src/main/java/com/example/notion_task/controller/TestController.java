package com.example.notion_task.controller;

import com.example.notion_task.model.Test;
import com.example.notion_task.notion.NotionClient;
import com.example.notion_task.notion.config.NotionConfigProperties;
import com.example.notion_task.notion.model.Page;
import com.example.notion_task.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private final NotionClient client;
    private final NotionConfigProperties notionConfigProperties;

    public TestController(NotionConfigProperties notionConfigProperties, NotionClient client) {
        this.notionConfigProperties = notionConfigProperties;
        this.client = client;
    }

    @GetMapping()
    public List<Test> findAll() {
        List<Page> pages = client.databases.query(notionConfigProperties.databaseId());
        return pages.stream().map(TestService::mapPageToTest).toList();
    }
}
