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
 * OfficersRegisterDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-19T15:40:48.087886500+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class OfficersRegisterDTO {

  private String ssnNumber;

  private String role;

  private String email;

  private String password;

  public OfficersRegisterDTO ssnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
    return this;
  }

  /**
   * Get ssnNumber
   * @return ssnNumber
   */
  
  @Schema(name = "ssnNumber", example = "123-45-6789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ssnNumber")
  public String getSsnNumber() {
    return ssnNumber;
  }

  public void setSsnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
  }

  public OfficersRegisterDTO role(String role) {
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

  public OfficersRegisterDTO email(String email) {
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

  public OfficersRegisterDTO password(String password) {
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
    OfficersRegisterDTO officersRegisterDTO = (OfficersRegisterDTO) o;
    return Objects.equals(this.ssnNumber, officersRegisterDTO.ssnNumber) &&
        Objects.equals(this.role, officersRegisterDTO.role) &&
        Objects.equals(this.email, officersRegisterDTO.email) &&
        Objects.equals(this.password, officersRegisterDTO.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ssnNumber, role, email, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OfficersRegisterDTO {\n");
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

