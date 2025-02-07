package com.ems.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    @JsonFormat(pattern = "dd-MMM-yyyy 'at' hh:mm:ss a")
    private LocalDateTime requestTime;
}
