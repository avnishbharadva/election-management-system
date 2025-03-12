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
 * Represents a record of name change history for a voter
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Schema(name = "nameHistoryDTO", description = "Represents a record of name change history for a voter")
@JsonTypeName("nameHistoryDTO")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-12T15:11:44.617977500+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class NameHistoryDTO {

  private String firstName;

  private String middleName;

  private String lastName;

  private String suffixName;

  public NameHistoryDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Voter's first name
   * @return firstName
   */
  
  @Schema(name = "firstName", example = "John", description = "Voter's first name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public NameHistoryDTO middleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * Voter's middle name (if any)
   * @return middleName
   */
  
  @Schema(name = "middleName", example = "A.", description = "Voter's middle name (if any)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("middleName")
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public NameHistoryDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Voter's last name
   * @return lastName
   */
  
  @Schema(name = "lastName", example = "Doe", description = "Voter's last name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public NameHistoryDTO suffixName(String suffixName) {
    this.suffixName = suffixName;
    return this;
  }

  /**
   * Suffix of the voter's name (if any)
   * @return suffixName
   */
  
  @Schema(name = "suffixName", example = "Jr.", description = "Suffix of the voter's name (if any)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("suffixName")
  public String getSuffixName() {
    return suffixName;
  }

  public void setSuffixName(String suffixName) {
    this.suffixName = suffixName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NameHistoryDTO nameHistoryDTO = (NameHistoryDTO) o;
    return Objects.equals(this.firstName, nameHistoryDTO.firstName) &&
        Objects.equals(this.middleName, nameHistoryDTO.middleName) &&
        Objects.equals(this.lastName, nameHistoryDTO.lastName) &&
        Objects.equals(this.suffixName, nameHistoryDTO.suffixName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, middleName, lastName, suffixName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NameHistoryDTO {\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    middleName: ").append(toIndentedString(middleName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    suffixName: ").append(toIndentedString(suffixName)).append("\n");
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

