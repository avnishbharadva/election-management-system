package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Represents a record of status change history for a voter
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Schema(name = "statusHistoryDTO", description = "Represents a record of status change history for a voter")
@JsonTypeName("statusHistoryDTO")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-12T15:11:44.617977500+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class StatusHistoryDTO {

  private String status;

  public StatusHistoryDTO status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Voter's status
   * @return status
   */
  
  @Schema(name = "status", example = "Active", description = "Voter's status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatusHistoryDTO statusHistoryDTO = (StatusHistoryDTO) o;
    return Objects.equals(this.status, statusHistoryDTO.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatusHistoryDTO {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

