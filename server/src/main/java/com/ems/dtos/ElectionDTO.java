package com.ems.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ElectionDTO {

    private Long electionId;

    @Size(max = 255, message = "Election name too long")
    private String electionName;

    @Size(max = 100, message = "Election type too long")
    private String electionType;

    @Future(message = "Election date must be in the future")
    private Date electionDate;

    @Size(max = 100, message = "State name too long")
    private String electionState;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    private List<Long> candidateIds;
}
