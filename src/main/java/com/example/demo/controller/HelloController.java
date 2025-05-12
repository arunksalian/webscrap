package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello Controller", description = "APIs for greeting messages")
public class HelloController {

    @Operation(
        summary = "Get a greeting message",
        description = "Returns a simple greeting message from the server"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved the greeting message"
    )
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
} 