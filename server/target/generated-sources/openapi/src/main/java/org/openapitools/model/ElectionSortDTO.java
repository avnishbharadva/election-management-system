package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ElectionSortDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-12T17:41:00.188687600+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class ElectionSortDTO {

  private Integer electionId;

  private String electionName;

  private String electionType;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate electionDate;

  private String electionState;

  private Integer totalSeats;

  public ElectionSortDTO electionId(Integer electionId) {
    this.electionId = electionId;
    return this;
  }

  /**
   * Get electionId
   * @return electionId
   */
  
  @Schema(name = "electionId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionId")
  public Integer getElectionId() {
    return electionId;
  }

  public void setElectionId(Integer electionId) {
    this.electionId = electionId;
  }

  public ElectionSortDTO electionName(String electionName) {
    this.electionName = electionName;
    return this;
  }

  /**
   * Get electionName
   * @return electionName
   */
  
  @Schema(name = "electionName", example = "Presidential Election", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionName")
  public String getElectionName() {
    return electionName;
  }

  public void setElectionName(String electionName) {
    this.electionName = electionName;
  }

  public ElectionSortDTO electionType(String electionType) {
    this.electionType = electionType;
    return this;
  }

  /**
   * Get electionType
   * @return electionType
   */
  
  @Schema(name = "electionType", example = "General", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionType")
  public String getElectionType() {
    return electionType;
  }

  public void setElectionType(String electionType) {
    this.electionType = electionType;
  }

  public ElectionSortDTO electionDate(LocalDate electionDate) {
    this.electionDate = electionDate;
    return this;
  }

  /**
   * Get electionDate
   * @return electionDate
   */
  @Valid 
  @Schema(name = "electionDate", example = "Fri Oct 10 05:30:00 IST 2025", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionDate")
  public LocalDate getElectionDate() {
    return electionDate;
  }

  public void setElectionDate(LocalDate electionDate) {
    this.electionDate = electionDate;
  }

  public ElectionSortDTO electionState(String electionState) {
    this.electionState = electionState;
    return this;
  }

  /**
   * Get electionState
   * @return electionState
   */
  
  @Schema(name = "electionState", example = "California", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionState")
  public String getElectionState() {
    return electionState;
  }

  public void setElectionState(String electionState) {
    this.electionState = electionState;
  }

  public ElectionSortDTO totalSeats(Integer totalSeats) {
    this.totalSeats = totalSeats;
    return this;
  }

  /**
   * Get totalSeats
   * @return totalSeats
   */
  
  @Schema(name = "totalSeats", example = "100", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalSeats")
  public Integer getTotalSeats() {
    return totalSeats;
  }

  public void setTotalSeats(Integer totalSeats) {
    this.totalSeats = totalSeats;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ElectionSortDTO electionSortDTO = (ElectionSortDTO) o;
    return Objects.equals(this.electionId, electionSortDTO.electionId) &&
        Objects.equals(this.electionName, electionSortDTO.electionName) &&
        Objects.equals(this.electionType, electionSortDTO.electionType) &&
        Objects.equals(this.electionDate, electionSortDTO.electionDate) &&
        Objects.equals(this.electionState, electionSortDTO.electionState) &&
        Objects.equals(this.totalSeats, electionSortDTO.totalSeats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(electionId, electionName, electionType, electionDate, electionState, totalSeats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ElectionSortDTO {\n");
    sb.append("    electionId: ").append(toIndentedString(electionId)).append("\n");
    sb.append("    electionName: ").append(toIndentedString(electionName)).append("\n");
    sb.append("    electionType: ").append(toIndentedString(electionType)).append("\n");
    sb.append("    electionDate: ").append(toIndentedString(electionDate)).append("\n");
    sb.append("    electionState: ").append(toIndentedString(electionState)).append("\n");
    sb.append("    totalSeats: ").append(toIndentedString(totalSeats)).append("\n");
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

