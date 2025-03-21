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
 * VoterDataDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@JsonTypeName("voterDataDTO")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T21:34:08.123886700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class VoterDataDTO {

  private String voterId;

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

  private String party;

  private AddressDTO residentialAddress;

  private AddressDTO mailingAddress;

  private String image;

  private String signature;

  private String status;

  public VoterDataDTO voterId(String voterId) {
    this.voterId = voterId;
    return this;
  }

  /**
   * Get voterId
   * @return voterId
   */
  
  @Schema(name = "voterId", example = "000123456", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("voterId")
  public String getVoterId() {
    return voterId;
  }

  public void setVoterId(String voterId) {
    this.voterId = voterId;
  }

  public VoterDataDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   */
  
  @Schema(name = "firstName", example = "John", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public VoterDataDTO middleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * Get middleName
   * @return middleName
   */
  
  @Schema(name = "middleName", example = "A.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("middleName")
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public VoterDataDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   */
  
  @Schema(name = "lastName", example = "Doe", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public VoterDataDTO suffixName(String suffixName) {
    this.suffixName = suffixName;
    return this;
  }

  /**
   * Get suffixName
   * @return suffixName
   */
  
  @Schema(name = "suffixName", example = "Jr.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("suffixName")
  public String getSuffixName() {
    return suffixName;
  }

  public void setSuffixName(String suffixName) {
    this.suffixName = suffixName;
  }

  public VoterDataDTO dateOfBirth(LocalDate dateOfBirth) {
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

  public VoterDataDTO gender(GenderEnum gender) {
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

  public VoterDataDTO dmvNumber(String dmvNumber) {
    this.dmvNumber = dmvNumber;
    return this;
  }

  /**
   * Get dmvNumber
   * @return dmvNumber
   */
  
  @Schema(name = "dmvNumber", example = "123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dmvNumber")
  public String getDmvNumber() {
    return dmvNumber;
  }

  public void setDmvNumber(String dmvNumber) {
    this.dmvNumber = dmvNumber;
  }

  public VoterDataDTO ssnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
    return this;
  }

  /**
   * Get ssnNumber
   * @return ssnNumber
   */
  
  @Schema(name = "ssnNumber", example = "987654321", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ssnNumber")
  public String getSsnNumber() {
    return ssnNumber;
  }

  public void setSsnNumber(String ssnNumber) {
    this.ssnNumber = ssnNumber;
  }

  public VoterDataDTO email(String email) {
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

  public VoterDataDTO phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   */
  
  @Schema(name = "phoneNumber", example = "12345678901", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public VoterDataDTO hasVotedBefore(Boolean hasVotedBefore) {
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

  public VoterDataDTO firstVotedYear(Integer firstVotedYear) {
    this.firstVotedYear = firstVotedYear;
    return this;
  }

  /**
   * Get firstVotedYear
   * @return firstVotedYear
   */
  
  @Schema(name = "firstVotedYear", example = "2010", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstVotedYear")
  public Integer getFirstVotedYear() {
    return firstVotedYear;
  }

  public void setFirstVotedYear(Integer firstVotedYear) {
    this.firstVotedYear = firstVotedYear;
  }

  public VoterDataDTO party(String party) {
    this.party = party;
    return this;
  }

  /**
   * Get party
   * @return party
   */
  
  @Schema(name = "party", example = "Democratic Party", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("party")
  public String getParty() {
    return party;
  }

  public void setParty(String party) {
    this.party = party;
  }

  public VoterDataDTO residentialAddress(AddressDTO residentialAddress) {
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

  public VoterDataDTO mailingAddress(AddressDTO mailingAddress) {
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

  public VoterDataDTO image(String image) {
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

  public VoterDataDTO signature(String signature) {
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

  public VoterDataDTO status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  
  @Schema(name = "status", example = "Active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    VoterDataDTO voterDataDTO = (VoterDataDTO) o;
    return Objects.equals(this.voterId, voterDataDTO.voterId) &&
        Objects.equals(this.firstName, voterDataDTO.firstName) &&
        Objects.equals(this.middleName, voterDataDTO.middleName) &&
        Objects.equals(this.lastName, voterDataDTO.lastName) &&
        Objects.equals(this.suffixName, voterDataDTO.suffixName) &&
        Objects.equals(this.dateOfBirth, voterDataDTO.dateOfBirth) &&
        Objects.equals(this.gender, voterDataDTO.gender) &&
        Objects.equals(this.dmvNumber, voterDataDTO.dmvNumber) &&
        Objects.equals(this.ssnNumber, voterDataDTO.ssnNumber) &&
        Objects.equals(this.email, voterDataDTO.email) &&
        Objects.equals(this.phoneNumber, voterDataDTO.phoneNumber) &&
        Objects.equals(this.hasVotedBefore, voterDataDTO.hasVotedBefore) &&
        Objects.equals(this.firstVotedYear, voterDataDTO.firstVotedYear) &&
        Objects.equals(this.party, voterDataDTO.party) &&
        Objects.equals(this.residentialAddress, voterDataDTO.residentialAddress) &&
        Objects.equals(this.mailingAddress, voterDataDTO.mailingAddress) &&
        Objects.equals(this.image, voterDataDTO.image) &&
        Objects.equals(this.signature, voterDataDTO.signature) &&
        Objects.equals(this.status, voterDataDTO.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(voterId, firstName, middleName, lastName, suffixName, dateOfBirth, gender, dmvNumber, ssnNumber, email, phoneNumber, hasVotedBefore, firstVotedYear, party, residentialAddress, mailingAddress, image, signature, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoterDataDTO {\n");
    sb.append("    voterId: ").append(toIndentedString(voterId)).append("\n");
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
    sb.append("    party: ").append(toIndentedString(party)).append("\n");
    sb.append("    residentialAddress: ").append(toIndentedString(residentialAddress)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
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

