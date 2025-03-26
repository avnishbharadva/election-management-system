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
 * OfficersResponseDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-26T10:26:02.044165800+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class OfficersResponseDTO {

  private Long officerId;

  private String ssnNumber;

  private String role;

  private String email;

  private String password;

  public OfficersResponseDTO officerId(Long officerId) {
    this.officerId = officerId;
    return this;
  }

  /**
   * Get officerId
   * @return officerId
   */
  
  @Schema(name = "officerId", example = "101", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("officerId")
  public Long getOfficerId() {
    return officerId;
  }

  public void setOfficerId(Long officerId) {
    this.officerId = officerId;
  }

  public OfficersResponseDTO ssnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
    return this;
  }

  /**
   * Get ssnNumber
   * @return ssnNumber
   */
  
  @Schema(name = "ssnNumber", example = "123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ssnNumber")
  public String getSsnNumber() {
    return ssnNumber;
  }

  public void setSsnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
  }

  public OfficersResponseDTO role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   */
  
  @Schema(name = "role", example = "Admin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("role")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public OfficersResponseDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", example = "officer@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public OfficersResponseDTO password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  
  @Schema(name = "password", example = "SecurePass123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OfficersResponseDTO officersResponseDTO = (OfficersResponseDTO) o;
    return Objects.equals(this.officerId, officersResponseDTO.officerId) &&
        Objects.equals(this.ssnNumber, officersResponseDTO.ssnNumber) &&
        Objects.equals(this.role, officersResponseDTO.role) &&
        Objects.equals(this.email, officersResponseDTO.email) &&
        Objects.equals(this.password, officersResponseDTO.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(officerId, ssnNumber, role, email, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OfficersResponseDTO {\n");
    sb.append("    officerId: ").append(toIndentedString(officerId)).append("\n");
    sb.append("    ssnNumber: ").append(toIndentedString(ssnNumber)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

