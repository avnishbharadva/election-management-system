package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.ElectionSortDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ElectionPageResponse
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-19T15:26:48.630850700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class ElectionPageResponse {

  @Valid
  private List<@Valid ElectionSortDTO> elections = new ArrayList<>();

  private Integer currentPage;

  private Integer totalPages;

  private Integer totalRecords;

  private Integer perPage;

  public ElectionPageResponse elections(List<@Valid ElectionSortDTO> elections) {
    this.elections = elections;
    return this;
  }

  public ElectionPageResponse addElectionsItem(ElectionSortDTO electionsItem) {
    if (this.elections == null) {
      this.elections = new ArrayList<>();
    }
    this.elections.add(electionsItem);
    return this;
  }

  /**
   * Get elections
   * @return elections
   */
  @Valid 
  @Schema(name = "elections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("elections")
  public List<@Valid ElectionSortDTO> getElections() {
    return elections;
  }

  public void setElections(List<@Valid ElectionSortDTO> elections) {
    this.elections = elections;
  }

  public ElectionPageResponse currentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return this;
  }

  /**
   * Get currentPage
   * @return currentPage
   */
  
  @Schema(name = "currentPage", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currentPage")
  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public ElectionPageResponse totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * @return totalPages
   */
  
  @Schema(name = "totalPages", example = "5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public ElectionPageResponse totalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
    return this;
  }

  /**
   * Get totalRecords
   * @return totalRecords
   */
  
  @Schema(name = "totalRecords", example = "100", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalRecords")
  public Integer getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
  }

  public ElectionPageResponse perPage(Integer perPage) {
    this.perPage = perPage;
    return this;
  }

  /**
   * Get perPage
   * @return perPage
   */
  
  @Schema(name = "perPage", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("perPage")
  public Integer getPerPage() {
    return perPage;
  }

  public void setPerPage(Integer perPage) {
    this.perPage = perPage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ElectionPageResponse electionPageResponse = (ElectionPageResponse) o;
    return Objects.equals(this.elections, electionPageResponse.elections) &&
        Objects.equals(this.currentPage, electionPageResponse.currentPage) &&
        Objects.equals(this.totalPages, electionPageResponse.totalPages) &&
        Objects.equals(this.totalRecords, electionPageResponse.totalRecords) &&
        Objects.equals(this.perPage, electionPageResponse.perPage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(elections, currentPage, totalPages, totalRecords, perPage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ElectionPageResponse {\n");
    sb.append("    elections: ").append(toIndentedString(elections)).append("\n");
    sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    totalRecords: ").append(toIndentedString(totalRecords)).append("\n");
    sb.append("    perPage: ").append(toIndentedString(perPage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

