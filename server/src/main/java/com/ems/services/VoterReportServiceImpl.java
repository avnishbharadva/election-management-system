package com.ems.services;

import com.ems.entities.Voter;
import com.ems.repositories.VoterRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VoterReportServiceImpl {

    private static final String path = "D:\\Spring\\election-management-system\\server\\src\\main\\resources\\static\\Reports\\VoterReport.jrxml";
    private static final String outputFile = "D:\\Spring\\election-management-system\\server\\src\\main\\resources\\static\\Reports";
    private final VoterRepository voterRepository;

    public String generateReport(String format) throws FileNotFoundException, JRException {
        List<Voter> list = voterRepository.findAll();
        File file = ResourceUtils.getFile(path);
        JasperDesign jasperDesign;
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Try");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        if (format.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + "\\new.pdf");
        }
        if (format.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, outputFile + "\\new.html");
        }
        return "";
    }

}

