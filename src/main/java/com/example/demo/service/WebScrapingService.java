package com.example.demo.service;

import com.example.demo.model.ScrapedData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WebScrapingService {
    private static final Logger logger = LoggerFactory.getLogger(WebScrapingService.class);

    public ScrapedData scrapeWebsite(String url) throws IOException {
        logger.info("Scraping website: {}", url);
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();
            logger.info("Successfully scraped website: {}", url);
            return new ScrapedData(
                document.title(),
                document.body().text(),
                url,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
        } catch (IOException e) {
            logger.error("Error scraping website: {}", url, e);
            throw e;
        }
    }

    public String scrapeSpecificElement(String url, String cssSelector) throws IOException {
        logger.info("Scraping element '{}' from website: {}", cssSelector, url);
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();
            String result = document.select(cssSelector).text();
            logger.info("Successfully scraped element '{}' from website: {}", cssSelector, url);
            return result;
        } catch (IOException e) {
            logger.error("Error scraping element '{}' from website: {}", cssSelector, url, e);
            throw e;
        }
    }
} 