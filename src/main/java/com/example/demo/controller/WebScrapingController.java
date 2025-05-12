package com.example.demo.controller;

import com.example.demo.model.ScrapedData;
import com.example.demo.service.WebScrapingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scrape")
@Tag(name = "Web Scraping Controller", description = "APIs for web scraping operations")
public class WebScrapingController {

    private static final Logger logger = LoggerFactory.getLogger(WebScrapingController.class);

    @Autowired
    private WebScrapingService webScrapingService;

    @Operation(
        summary = "Scrape entire website",
        description = "Scrapes the entire content of a website including title and body text"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully scraped the website"
    )
    @GetMapping(value = "/website", produces = "application/json")
    public ResponseEntity<ScrapedData> scrapeWebsite(
            @Parameter(description = "URL of the website to scrape")
            @RequestParam String url) {
        logger.info("Received request to scrape website: {}", url);
        try {
            ScrapedData scrapedData = webScrapingService.scrapeWebsite(url);
            return ResponseEntity.ok(scrapedData);
        } catch (Exception e) {
            logger.error("Error in scrapeWebsite for url {}: {}", url, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Scrape specific element",
        description = "Scrapes a specific element from a website using CSS selector"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully scraped the specific element"
    )
    @GetMapping(value = "/element", produces = "application/json")
    public ResponseEntity<String> scrapeElement(
            @Parameter(description = "URL of the website to scrape")
            @RequestParam String url,
            @Parameter(description = "CSS selector for the element to scrape")
            @RequestParam String cssSelector) {
        logger.info("Received request to scrape element '{}' from website: {}", cssSelector, url);
        try {
            String content = webScrapingService.scrapeSpecificElement(url, cssSelector);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            logger.error("Error in scrapeElement for url {}, selector {}: {}", url, cssSelector, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
} 