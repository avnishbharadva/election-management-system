package com.ems.controllers;

import com.ems.services.VoterHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voters/history")
@RequiredArgsConstructor
public class VoterHistoryController {
    private final VoterHistoryService voterHistoryService;

    @GetMapping("/{voterId}")
    public ResponseEntity<List<Object[]>> getVoterHistoryById(@PathVariable String voterId){
        return ResponseEntity.ok(voterHistoryService.getVoterHistoryId(voterId));
    }
}
