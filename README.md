# Spring Boot Web Scraping Application

This project is a Spring Boot application that provides REST APIs for web scraping. It uses JSoup for HTML parsing and includes Swagger for API documentation.

## Features

- Scrape entire websites and specific elements using CSS selectors.
- JSON response format.
- Swagger UI for API documentation.
- Logging for tracking requests and errors.

## Prerequisites

- Java 17
- Maven

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/arunksalian/webscrap.git
   cd webscrap
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage

- Access Swagger UI: http://localhost:8080/swagger-ui.html
- Scrape an entire website: GET http://localhost:8080/api/scrape/website?url=https://example.com
- Scrape a specific element: GET http://localhost:8080/api/scrape/element?url=https://example.com&cssSelector=p

## License

This project is licensed under the MIT License.
