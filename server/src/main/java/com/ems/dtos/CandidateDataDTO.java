package com.ems.dtos;

import lombok.*;

@RequiredArgsConstructor
@Getter@Setter
@AllArgsConstructor
public class CandidateDataDTO {
    private CandidateDTO candidate;
    private String candidateImage;
    private String candidateSignature;
}