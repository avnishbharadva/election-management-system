package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalTime;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ErrorResponse
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-12T15:11:49.516614600+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class ErrorResponse {

  private String message;

  private LocalTime timestamp;

  public ErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Detailed error message
   * @return message
   */
  
  @Schema(name = "message", example = "Detailed error message", description = "Detailed error message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorResponse timestamp(LocalTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Timestamp of when the error occurred
   * @return timestamp
   */
  @Valid 
  @Schema(name = "timestamp", example = "10:30:15", description = "Timestamp of when the error occurred", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timestamp")
  public LocalTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalTime timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.message, errorResponse.message) &&
        Objects.equals(this.timestamp, errorResponse.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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

