package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScrapedData {
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("timestamp")
    private String timestamp;

    public ScrapedData() {
    }

    public ScrapedData(String title, String content, String url, String timestamp) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.timestamp = timestamp;
    }
} 