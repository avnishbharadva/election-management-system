package com.ems.dtos;
import lombok.Data;
import java.time.LocalDate;

@Data
public class VoterSearchDTO {
    
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String dmvNumber;
    private String ssnNumber;
}