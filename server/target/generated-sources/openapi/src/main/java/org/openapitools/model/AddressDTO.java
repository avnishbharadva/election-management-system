package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddressDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T21:34:08.123886700+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class AddressDTO {

  private String addressLine;

  private String aptNumber;

  private String city;

  private String state;

  private String zipCode;

  /**
   * Gets or Sets addressType
   */
  public enum AddressTypeEnum {
    RESIDENTIAL("RESIDENTIAL"),
    
    MAILING("MAILING");

    private String value;

    AddressTypeEnum(String value) {
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
    public static AddressTypeEnum fromValue(String value) {
      for (AddressTypeEnum b : AddressTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private AddressTypeEnum addressType;

  private String county;

  private String town;

  public AddressDTO addressLine(String addressLine) {
    this.addressLine = addressLine;
    return this;
  }

  /**
   * Get addressLine
   * @return addressLine
   */
  
  @Schema(name = "addressLine", example = "123 Main St", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("addressLine")
  public String getAddressLine() {
    return addressLine;
  }

  public void setAddressLine(String addressLine) {
    this.addressLine = addressLine;
  }

  public AddressDTO aptNumber(String aptNumber) {
    this.aptNumber = aptNumber;
    return this;
  }

  /**
   * Get aptNumber
   * @return aptNumber
   */
  
  @Schema(name = "aptNumber", example = "Apt 4B", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aptNumber")
  public String getAptNumber() {
    return aptNumber;
  }

  public void setAptNumber(String aptNumber) {
    this.aptNumber = aptNumber;
  }

  public AddressDTO city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  
  @Schema(name = "city", example = "Springfield", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public AddressDTO state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   */
  
  @Schema(name = "state", example = "New York", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("state")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public AddressDTO zipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

  /**
   * Get zipCode
   * @return zipCode
   */
  @Pattern(regexp = "\\d{5}") 
  @Schema(name = "zipCode", example = "62704", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("zipCode")
  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public AddressDTO addressType(AddressTypeEnum addressType) {
    this.addressType = addressType;
    return this;
  }

  /**
   * Get addressType
   * @return addressType
   */
  
  @Schema(name = "addressType", example = "RESIDENTIAL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("addressType")
  public AddressTypeEnum getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressTypeEnum addressType) {
    this.addressType = addressType;
  }

  public AddressDTO county(String county) {
    this.county = county;
    return this;
  }

  /**
   * Get county
   * @return county
   */
  
  @Schema(name = "county", example = "Las Vegas", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("county")
  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public AddressDTO town(String town) {
    this.town = town;
    return this;
  }

  /**
   * Get town
   * @return town
   */
  
  @Schema(name = "town", example = "Alexander Street", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("town")
  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressDTO addressDTO = (AddressDTO) o;
    return Objects.equals(this.addressLine, addressDTO.addressLine) &&
        Objects.equals(this.aptNumber, addressDTO.aptNumber) &&
        Objects.equals(this.city, addressDTO.city) &&
        Objects.equals(this.state, addressDTO.state) &&
        Objects.equals(this.zipCode, addressDTO.zipCode) &&
        Objects.equals(this.addressType, addressDTO.addressType) &&
        Objects.equals(this.county, addressDTO.county) &&
        Objects.equals(this.town, addressDTO.town);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressLine, aptNumber, city, state, zipCode, addressType, county, town);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressDTO {\n");
    sb.append("    addressLine: ").append(toIndentedString(addressLine)).append("\n");
    sb.append("    aptNumber: ").append(toIndentedString(aptNumber)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    county: ").append(toIndentedString(county)).append("\n");
    sb.append("    town: ").append(toIndentedString(town)).append("\n");
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

