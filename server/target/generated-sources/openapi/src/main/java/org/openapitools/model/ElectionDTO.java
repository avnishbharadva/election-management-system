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
 * ElectionDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-19T15:40:47.427401700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class ElectionDTO {

  private Long electionId = null;

  private String electionName;

  private String electionType;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate electionDate;

  private String electionState;

  private Integer totalSeats;

  public ElectionDTO electionId(Long electionId) {
    this.electionId = electionId;
    return this;
  }

  /**
   * Get electionId
   * @return electionId
   */
  
  @Schema(name = "electionId", example = "101", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionId")
  public Long getElectionId() {
    return electionId;
  }

  public void setElectionId(Long electionId) {
    this.electionId = electionId;
  }

  public ElectionDTO electionName(String electionName) {
    this.electionName = electionName;
    return this;
  }

  /**
   * Get electionName
   * @return electionName
   */
  @Size(max = 255) 
  @Schema(name = "electionName", example = "Presidential Election", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionName")
  public String getElectionName() {
    return electionName;
  }

  public void setElectionName(String electionName) {
    this.electionName = electionName;
  }

  public ElectionDTO electionType(String electionType) {
    this.electionType = electionType;
    return this;
  }

  /**
   * Get electionType
   * @return electionType
   */
  @Size(max = 100) 
  @Schema(name = "electionType", example = "General", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionType")
  public String getElectionType() {
    return electionType;
  }

  public void setElectionType(String electionType) {
    this.electionType = electionType;
  }

  public ElectionDTO electionDate(LocalDate electionDate) {
    this.electionDate = electionDate;
    return this;
  }

  /**
   * Get electionDate
   * @return electionDate
   */
  @Valid 
  @Schema(name = "electionDate", example = "Mon Nov 03 05:30:00 IST 2025", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionDate")
  public LocalDate getElectionDate() {
    return electionDate;
  }

  public void setElectionDate(LocalDate electionDate) {
    this.electionDate = electionDate;
  }

  public ElectionDTO electionState(String electionState) {
    this.electionState = electionState;
    return this;
  }

  /**
   * Get electionState
   * @return electionState
   */
  @Size(max = 100) 
  @Schema(name = "electionState", example = "California", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("electionState")
  public String getElectionState() {
    return electionState;
  }

  public void setElectionState(String electionState) {
    this.electionState = electionState;
  }

  public ElectionDTO totalSeats(Integer totalSeats) {
    this.totalSeats = totalSeats;
    return this;
  }

  /**
   * Get totalSeats
   * minimum: 1
   * @return totalSeats
   */
  @Min(1) 
  @Schema(name = "totalSeats", example = "50", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    ElectionDTO electionDTO = (ElectionDTO) o;
    return Objects.equals(this.electionId, electionDTO.electionId) &&
        Objects.equals(this.electionName, electionDTO.electionName) &&
        Objects.equals(this.electionType, electionDTO.electionType) &&
        Objects.equals(this.electionDate, electionDTO.electionDate) &&
        Objects.equals(this.electionState, electionDTO.electionState) &&
        Objects.equals(this.totalSeats, electionDTO.totalSeats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(electionId, electionName, electionType, electionDate, electionState, totalSeats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ElectionDTO {\n");
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

