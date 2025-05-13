package com.example.demo.service;

import com.example.demo.model.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AlibabaScraperService {

    private static final String SEARCH_URL = "https://www.alibaba.com/trade/search?fsb=y&IndexArea=product_en&CatId=&SearchText=%s&viewtype=G";
    private static final String[] USER_AGENTS = {
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Edge/120.0.0.0"
    };

    public List<Supplier> searchSuppliers(String searchTerm) {
        try {
            String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
            String searchUrl = String.format(SEARCH_URL, encodedSearchTerm);
            String userAgent = USER_AGENTS[new Random().nextInt(USER_AGENTS.length)];

            log.debug("Searching for suppliers with term: {}", searchTerm);
            log.debug("Using URL: {}", searchUrl);

            Document doc = Jsoup.connect(searchUrl)
                    .userAgent(userAgent)
                    .timeout(30000)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Cache-Control", "no-cache")
                    .header("Pragma", "no-cache")
                    .header("Sec-Fetch-Dest", "document")
                    .header("Sec-Fetch-Mode", "navigate")
                    .header("Sec-Fetch-Site", "none")
                    .header("Sec-Fetch-User", "?1")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("DNT", "1")
                    .header("Sec-GPC", "1")
                    .header("Cookie", "ali_apache_id=" + System.currentTimeMillis())
                    .referrer("https://www.alibaba.com/")
                    .maxBodySize(0)
                    .followRedirects(true)
                    .get();

           // log.info("document: {}", doc);  
            // Save response for debugging
            String debugFilePath = "debug_response.html";
            try (java.io.FileWriter writer = new java.io.FileWriter(debugFilePath)) {
                writer.write(doc.outerHtml());
                log.debug("Saved HTML response to {}", debugFilePath);
            }

            log.debug("Successfully fetched search results page");

            List<Supplier> suppliers = new ArrayList<>();
            //Elements productCards = doc.select(".search-card-e-company");
            Elements productCards = doc.select(".gallery-card-layout-info");
            log.debug("Found {} product cards", productCards.size());

            for (Element card : productCards) {
                log.debug("product card: {}", card);
                try {
                    Supplier supplier = extractSupplierInfo(card);
                    if (supplier != null) {
                        suppliers.add(supplier);
                    }
                } catch (Exception e) {
                    log.warn("Failed to extract supplier info from card: {}", e.getMessage());
                }
            }

            log.info("Successfully extracted {} suppliers", suppliers.size());
            if (suppliers.isEmpty()) {
                log.info("No suppliers found - this may be due to Alibaba's anti-scraping measures");
            }
            return suppliers;

        } catch (IOException e) {
            log.error("Error fetching suppliers from Alibaba: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch suppliers from Alibaba", e);
        }
    }

    private Supplier extractSupplierInfo(Element card) {
        try {
            // Get supplier name from the company name element
            Element companyElement = card.select(".search-card-e-company").first();
            String name = companyElement != null ? companyElement.text() : "";
            log.debug("Found supplier name: {}", name);

            // Get price from the price element
            Element priceElement = card.select(".search-card-e-price-main").first();
            String price = priceElement != null ? priceElement.text() : "";
            log.debug("Found price: {}", price);

            // Create and return supplier object
            return Supplier.builder()
                    .name(name)
                    .price(price)
                    .build();

        } catch (Exception e) {
            log.warn("Failed to extract supplier info: {}", e.getMessage());
            return null;
        }
    }
} 