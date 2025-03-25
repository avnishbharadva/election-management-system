package com.ems.services.impls;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.VoterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class VoterReportServiceImpl {

    private static final String path = "D:\\Spring\\election-management-system\\server\\src\\main\\resources\\static\\Reports\\VoterReport.jrxml";
    private static final String outputFile = "D:\\Spring\\election-management-system\\server\\src\\main\\resources\\static\\Reports";
    private final VoterRepository voterRepository;
    private final AddressRepository addressRepository;

    public void generateReport(String format) throws FileNotFoundException, JRException {
        List<Voter> list = voterRepository.findAll();
        File file = ResourceUtils.getFile(path);

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
    }

    public String generateVoterAddressReport(String format, String startVoterId, String endVoterId) throws FileNotFoundException, JRException {
        List<Address> addressList = addressRepository.findAddressesByVoterIdRange(startVoterId, endVoterId);
        log.info("Address Report Creating");

        File file = ResourceUtils.getFile(path + "\\AddressFormat.jrxml");
        JasperDesign jasperDesign;
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(addressList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "EMS");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
        if (format.equalsIgnoreCase("pdf")) {
            log.info("Report is Generated In Pdf Format");
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + "\\AddressReport.pdf");
        }
        if (format.equalsIgnoreCase("html")) {
            log.info("Report is Generated In HTMl Format");
            JasperExportManager.exportReportToHtmlFile(jasperPrint, outputFile + "\\AddressReport.html");
        }
        return "Created";
    }


}

