package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

@Schema(name = "PartyListDTO", description = "Party Response format")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-25T10:50:58.672811700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class PartyListDTO {

  private String message;

  @Valid
  private List<PartyDataDTO> data = new ArrayList<>();

  public PartyListDTO message(String message) {
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

  public PartyListDTO data(List<PartyDataDTO> data) {
    this.data = data;
    return this;
  }

  public PartyListDTO addDataItem(PartyDataDTO dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * All Party Details
   * @return data
   */
  @Valid 
  @Schema(name = "data", description = "All Party Details", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public List<PartyDataDTO> getData() {
    return data;
  }

  public void setData(List<PartyDataDTO> data) {
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
    PartyListDTO partyListDTO = (PartyListDTO) o;
    return Objects.equals(this.message, partyListDTO.message) &&
        Objects.equals(this.data, partyListDTO.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartyListDTO {\n");
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

