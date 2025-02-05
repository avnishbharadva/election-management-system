package com.ems.dtos;

import lombok.Data;

@Data
public class CandidateErrorResponse {

    private int Status;
    private String message;
    private long timestamp;
}
