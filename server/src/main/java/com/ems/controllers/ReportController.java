package com.ems.controllers;

import com.ems.services.impls.VoterReportServiceImpl;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final VoterReportServiceImpl voterReport;

    @GetMapping("/report/{format}")
    public String generateVoterReportApi(@PathVariable String format) throws JRException, FileNotFoundException, FileNotFoundException {
        voterReport.generateReport(format);
        return "Created...";
    }
}
