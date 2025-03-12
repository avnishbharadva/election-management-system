package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Party Response format
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Schema(name = "PartyDTO", description = "Party Response format")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-12T17:41:01.252272900+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class PartyDTO {

  private String message;

  private PartyDataDTO data;

  public PartyDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Operation Response message
   * @return message
   */
  
  @Schema(name = "message", example = "Success", description = "Operation Response message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public PartyDTO data(PartyDataDTO data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @Valid 
  @Schema(name = "data", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public PartyDataDTO getData() {
    return data;
  }

  public void setData(PartyDataDTO data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartyDTO partyDTO = (PartyDTO) o;
    return Objects.equals(this.message, partyDTO.message) &&
        Objects.equals(this.data, partyDTO.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartyDTO {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

