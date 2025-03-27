package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import org.openapitools.model.AddressDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * VoterUpdateRequest
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@JsonTypeName("voterUpdateRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-27T10:13:11.724384600+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class VoterUpdateRequest {

  private String firstName;

  private String middleName;

  private String lastName;

  private String suffixName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  /**
   * Gets or Sets gender
   */
  public enum GenderEnum {
    MALE("MALE"),
    
    FEMALE("FEMALE");

    private String value;

    GenderEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static GenderEnum fromValue(String value) {
      for (GenderEnum b : GenderEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private GenderEnum gender;

  private String dmvNumber;

  private String ssnNumber;

  private String email;

  private String phoneNumber;

  private Boolean hasVotedBefore;

  private Integer firstVotedYear;

  private Long partyId;

  private AddressDTO residentialAddress;

  private AddressDTO mailingAddress;

  private String image;

  private String signature;

  private Long statusId;

  public VoterUpdateRequest firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   */
  @Size(min = 2, max = 50) 
  @Schema(name = "firstName", example = "John", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public VoterUpdateRequest middleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * Get middleName
   * @return middleName
   */
  @Size(max = 20) 
  @Schema(name = "middleName", example = "A.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("middleName")
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public VoterUpdateRequest lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   */
  @Size(min = 2, max = 50) 
  @Schema(name = "lastName", example = "Doe", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public VoterUpdateRequest suffixName(String suffixName) {
    this.suffixName = suffixName;
    return this;
  }

  /**
   * Get suffixName
   * @return suffixName
   */
  @Size(max = 10) 
  @Schema(name = "suffixName", example = "Jr.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("suffixName")
  public String getSuffixName() {
    return suffixName;
  }

  public void setSuffixName(String suffixName) {
    this.suffixName = suffixName;
  }

  public VoterUpdateRequest dateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Get dateOfBirth
   * @return dateOfBirth
   */
  @Valid 
  @Schema(name = "dateOfBirth", example = "Tue May 15 05:30:00 IST 1990", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateOfBirth")
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public VoterUpdateRequest gender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
   */
  
  @Schema(name = "gender", example = "MALE", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gender")
  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public VoterUpdateRequest dmvNumber(String dmvNumber) {
    this.dmvNumber = dmvNumber;
    return this;
  }

  /**
   * Get dmvNumber
   * @return dmvNumber
   */
  @Pattern(regexp = "\\d{9}") 
  @Schema(name = "dmvNumber", example = "123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dmvNumber")
  public String getDmvNumber() {
    return dmvNumber;
  }

  public void setDmvNumber(String dmvNumber) {
    this.dmvNumber = dmvNumber;
  }

  public VoterUpdateRequest ssnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
    return this;
  }

  /**
   * Get ssnNumber
   * @return ssnNumber
   */
  @Pattern(regexp = "\\d{9}") 
  @Schema(name = "ssnNumber", example = "987654321", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ssnNumber")
  public String getSsnNumber() {
    return ssnNumber;
  }

  public void setSsnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
  }

  public VoterUpdateRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public VoterUpdateRequest phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   */
  @Pattern(regexp = "\\d{11}") 
  @Schema(name = "phoneNumber", example = "12345678901", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public VoterUpdateRequest hasVotedBefore(Boolean hasVotedBefore) {
    this.hasVotedBefore = hasVotedBefore;
    return this;
  }

  /**
   * Get hasVotedBefore
   * @return hasVotedBefore
   */
  
  @Schema(name = "hasVotedBefore", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("hasVotedBefore")
  public Boolean getHasVotedBefore() {
    return hasVotedBefore;
  }

  public void setHasVotedBefore(Boolean hasVotedBefore) {
    this.hasVotedBefore = hasVotedBefore;
  }

  public VoterUpdateRequest firstVotedYear(Integer firstVotedYear) {
    this.firstVotedYear = firstVotedYear;
    return this;
  }

  /**
   * Get firstVotedYear
   * minimum: 1900
   * maximum: 2025
   * @return firstVotedYear
   */
  @Min(1900) @Max(2025) 
  @Schema(name = "firstVotedYear", example = "2010", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstVotedYear")
  public Integer getFirstVotedYear() {
    return firstVotedYear;
  }

  public void setFirstVotedYear(Integer firstVotedYear) {
    this.firstVotedYear = firstVotedYear;
  }

  public VoterUpdateRequest partyId(Long partyId) {
    this.partyId = partyId;
    return this;
  }

  /**
   * Get partyId
   * @return partyId
   */
  
  @Schema(name = "partyId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyId")
  public Long getPartyId() {
    return partyId;
  }

  public void setPartyId(Long partyId) {
    this.partyId = partyId;
  }

  public VoterUpdateRequest residentialAddress(AddressDTO residentialAddress) {
    this.residentialAddress = residentialAddress;
    return this;
  }

  /**
   * Get residentialAddress
   * @return residentialAddress
   */
  @Valid 
  @Schema(name = "residentialAddress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("residentialAddress")
  public AddressDTO getResidentialAddress() {
    return residentialAddress;
  }

  public void setResidentialAddress(AddressDTO residentialAddress) {
    this.residentialAddress = residentialAddress;
  }

  public VoterUpdateRequest mailingAddress(AddressDTO mailingAddress) {
    this.mailingAddress = mailingAddress;
    return this;
  }

  /**
   * Get mailingAddress
   * @return mailingAddress
   */
  @Valid 
  @Schema(name = "mailingAddress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mailingAddress")
  public AddressDTO getMailingAddress() {
    return mailingAddress;
  }

  public void setMailingAddress(AddressDTO mailingAddress) {
    this.mailingAddress = mailingAddress;
  }

  public VoterUpdateRequest image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
   */
  
  @Schema(name = "image", example = "base64-encoded-image-string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image")
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public VoterUpdateRequest signature(String signature) {
    this.signature = signature;
    return this;
  }

  /**
   * Get signature
   * @return signature
   */
  
  @Schema(name = "signature", example = "base64-encoded-signature-string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("signature")
  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public VoterUpdateRequest statusId(Long statusId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VoterUpdateRequest voterUpdateRequest = (VoterUpdateRequest) o;
    return Objects.equals(this.firstName, voterUpdateRequest.firstName) &&
        Objects.equals(this.middleName, voterUpdateRequest.middleName) &&
        Objects.equals(this.lastName, voterUpdateRequest.lastName) &&
        Objects.equals(this.suffixName, voterUpdateRequest.suffixName) &&
        Objects.equals(this.dateOfBirth, voterUpdateRequest.dateOfBirth) &&
        Objects.equals(this.gender, voterUpdateRequest.gender) &&
        Objects.equals(this.dmvNumber, voterUpdateRequest.dmvNumber) &&
        Objects.equals(this.ssnNumber, voterUpdateRequest.ssnNumber) &&
        Objects.equals(this.email, voterUpdateRequest.email) &&
        Objects.equals(this.phoneNumber, voterUpdateRequest.phoneNumber) &&
        Objects.equals(this.hasVotedBefore, voterUpdateRequest.hasVotedBefore) &&
        Objects.equals(this.firstVotedYear, voterUpdateRequest.firstVotedYear) &&
        Objects.equals(this.partyId, voterUpdateRequest.partyId) &&
        Objects.equals(this.residentialAddress, voterUpdateRequest.residentialAddress) &&
        Objects.equals(this.mailingAddress, voterUpdateRequest.mailingAddress) &&
        Objects.equals(this.image, voterUpdateRequest.image) &&
        Objects.equals(this.signature, voterUpdateRequest.signature) &&
        Objects.equals(this.statusId, voterUpdateRequest.statusId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, middleName, lastName, suffixName, dateOfBirth, gender, dmvNumber, ssnNumber, email, phoneNumber, hasVotedBefore, firstVotedYear, partyId, residentialAddress, mailingAddress, image, signature, statusId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoterUpdateRequest {\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    middleName: ").append(toIndentedString(middleName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    suffixName: ").append(toIndentedString(suffixName)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    dmvNumber: ").append(toIndentedString(dmvNumber)).append("\n");
    sb.append("    ssnNumber: ").append(toIndentedString(ssnNumber)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    hasVotedBefore: ").append(toIndentedString(hasVotedBefore)).append("\n");
    sb.append("    firstVotedYear: ").append(toIndentedString(firstVotedYear)).append("\n");
    sb.append("    partyId: ").append(toIndentedString(partyId)).append("\n");
    sb.append("    residentialAddress: ").append(toIndentedString(residentialAddress)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    statusId: ").append(toIndentedString(statusId)).append("\n");
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

