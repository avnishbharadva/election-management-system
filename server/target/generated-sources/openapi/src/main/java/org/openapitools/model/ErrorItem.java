package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ErrorItem
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T19:12:03.599388100+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class ErrorItem {

  private String field;

  private String errorMessage;

  public ErrorItem field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
   */
  
  @Schema(name = "field", example = "partyName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("field")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public ErrorItem errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Get errorMessage
   * @return errorMessage
   */
  
  @Schema(name = "errorMessage", example = "Party name must be between 3 and 30 characters.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorItem errorItem = (ErrorItem) o;
    return Objects.equals(this.field, errorItem.field) &&
        Objects.equals(this.errorMessage, errorItem.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorItem {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
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

