package com.ems.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor  // ✅ Ensures all fields are set correctly
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    @JsonFormat(pattern = "dd-MMM-yyyy 'at' hh:mm:ss a")
    private LocalDateTime requestTime;
}
