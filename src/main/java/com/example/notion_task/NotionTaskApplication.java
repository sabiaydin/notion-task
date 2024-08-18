package com.example.notion_task;

import com.example.notion_task.notion.config.NotionConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NotionConfigProperties.class)
public class NotionTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotionTaskApplication.class, args);
	}

}
