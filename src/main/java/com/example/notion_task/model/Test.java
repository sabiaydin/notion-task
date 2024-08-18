package com.example.notion_task.model;

import java.time.LocalDateTime;

public record Test(String id, String title, LocalDateTime startDate,LocalDateTime endDate,String url,String recording) {

}
