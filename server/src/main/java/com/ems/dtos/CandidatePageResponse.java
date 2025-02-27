package com.ems.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CandidatePageResponse {
    private List<CandidateDetailsDTO> candidates;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalRecords;
    private Integer perPage;
}
