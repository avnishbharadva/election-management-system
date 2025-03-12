package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PaginatedVoterDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-12T17:40:59.128896300+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class PaginatedVoterDTO {

  @Valid
  private List<@Valid VoterDataDTO> data = new ArrayList<>();

  private Long totalElements;

  private Integer totalPages;

  private Integer size;

  private Integer number;

  public PaginatedVoterDTO data(List<@Valid VoterDataDTO> data) {
    this.data = data;
    return this;
  }

  public PaginatedVoterDTO addDataItem(VoterDataDTO dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @Valid 
  @Schema(name = "data", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public List<@Valid VoterDataDTO> getData() {
    return data;
  }

  public void setData(List<@Valid VoterDataDTO> data) {
    this.data = data;
  }

  public PaginatedVoterDTO totalElements(Long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  /**
   * Total number of voters found
   * @return totalElements
   */
  
  @Schema(name = "totalElements", description = "Total number of voters found", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalElements")
  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  public PaginatedVoterDTO totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Total pages available
   * @return totalPages
   */
  
  @Schema(name = "totalPages", description = "Total pages available", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public PaginatedVoterDTO size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Number of voters per page
   * @return size
   */
  
  @Schema(name = "size", description = "Number of voters per page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public PaginatedVoterDTO number(Integer number) {
    this.number = number;
    return this;
  }

  /**
   * Current page number
   * @return number
   */
  
  @Schema(name = "number", description = "Current page number", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("number")
  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaginatedVoterDTO paginatedVoterDTO = (PaginatedVoterDTO) o;
    return Objects.equals(this.data, paginatedVoterDTO.data) &&
        Objects.equals(this.totalElements, paginatedVoterDTO.totalElements) &&
        Objects.equals(this.totalPages, paginatedVoterDTO.totalPages) &&
        Objects.equals(this.size, paginatedVoterDTO.size) &&
        Objects.equals(this.number, paginatedVoterDTO.number);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, totalElements, totalPages, size, number);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaginatedVoterDTO {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
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

