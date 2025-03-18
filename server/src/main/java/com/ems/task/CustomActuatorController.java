package com.ems.task;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actuator/custom")
@RequiredArgsConstructor
public class CustomActuatorController {

    private final HealthEndpoint healthEndpoint;
    private final InfoEndpoint infoEndpoint;

    @GetMapping(produces = "application/json")
    public Map<String, Object> getCustomActuatorInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("health", healthEndpoint.health());
        response.put("info", infoEndpoint.info());
        return response;
    }
}