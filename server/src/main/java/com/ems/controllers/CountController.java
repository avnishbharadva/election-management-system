package com.ems.controllers;

import com.ems.services.CountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counts")
public class CountController {

    private final CountService countService;

    @GetMapping
    public ResponseEntity<Map<String,Long>> getAllCounts()
    {
        return ResponseEntity.ok(countService.getCounts());
    }
}
