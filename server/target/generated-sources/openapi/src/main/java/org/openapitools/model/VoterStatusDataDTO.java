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
 * VoterStatusDataDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@JsonTypeName("voterStatusDataDTO")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T21:34:08.123886700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class VoterStatusDataDTO {

  private Long statusId;

  private String statusDesc;

  public VoterStatusDataDTO statusId(Long statusId) {
    this.statusId = statusId;
    return this;
  }

  /**
   * Get statusId
   * @return statusId
   */
  
  @Schema(name = "statusId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statusId")
  public Long getStatusId() {
    return statusId;
  }

  public void setStatusId(Long statusId) {
    this.statusId = statusId;
  }

  public VoterStatusDataDTO statusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
    return this;
  }

  /**
   * Get statusDesc
   * @return statusDesc
   */
  
  @Schema(name = "statusDesc", example = "Active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statusDesc")
  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VoterStatusDataDTO voterStatusDataDTO = (VoterStatusDataDTO) o;
    return Objects.equals(this.statusId, voterStatusDataDTO.statusId) &&
        Objects.equals(this.statusDesc, voterStatusDataDTO.statusDesc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusId, statusDesc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoterStatusDataDTO {\n");
    sb.append("    statusId: ").append(toIndentedString(statusId)).append("\n");
    sb.append("    statusDesc: ").append(toIndentedString(statusDesc)).append("\n");
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

