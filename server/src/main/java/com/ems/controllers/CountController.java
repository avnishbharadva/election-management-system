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
<<<<<<< HEAD
@RequestMapping("/api/counts")
=======
@RequestMapping("/counts")
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
public class CountController {

    private final CountService countService;

    @GetMapping
    public ResponseEntity<Map<String,Long>> getAllCounts()
    {
        return ResponseEntity.ok(countService.getCounts());
    }
}
