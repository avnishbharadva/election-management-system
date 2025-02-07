package com.ems.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CandidateErrorResponse {

    private int status;
    private String message;

    @JsonFormat(pattern = "dd-MM-yyyy 'at' hh:mm:ss a" )
    private LocalDateTime timestamp;
}
