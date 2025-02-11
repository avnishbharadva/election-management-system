package com.ems.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CandidatePageResponse {
    private List<CandidateDTO> candidates;
    private int currentPage;
    private int totalPages;
    private long totalRecords;
    private int perPage;
}
