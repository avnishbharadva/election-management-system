package com.ems.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ElectionPageResponse {
    private List<ElectionSortDTO> election;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalRecords;
    private Integer perPage;
}
