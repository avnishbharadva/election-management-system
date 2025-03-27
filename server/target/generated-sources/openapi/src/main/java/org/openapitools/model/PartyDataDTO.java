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
 * PartyDataDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-27T10:13:13.737364+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class PartyDataDTO {

  private String partyName;

  private String partyAbbreviation;

  private String partySymbol;

  private String partyFounderName;

  private Integer partyFoundationYear;

  private String partyLeaderName;

  private String partyWebSite;

  private String headQuarters;

  private Long partyId;

  public PartyDataDTO partyName(String partyName) {
    this.partyName = partyName;
    return this;
  }

  /**
   * Party name must be between 3 and 30 characters.
   * @return partyName
   */
  @Size(min = 3, max = 30) 
  @Schema(name = "partyName", example = "Democratic Alliance", description = "Party name must be between 3 and 30 characters.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyName")
  public String getPartyName() {
    return partyName;
  }

  public void setPartyName(String partyName) {
    this.partyName = partyName;
  }

  public PartyDataDTO partyAbbreviation(String partyAbbreviation) {
    this.partyAbbreviation = partyAbbreviation;
    return this;
  }

  /**
   * Party abbreviation must not exceed 10 characters.
   * @return partyAbbreviation
   */
  @Size(min = 1, max = 10) 
  @Schema(name = "partyAbbreviation", example = "DA", description = "Party abbreviation must not exceed 10 characters.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyAbbreviation")
  public String getPartyAbbreviation() {
    return partyAbbreviation;
  }

  public void setPartyAbbreviation(String partyAbbreviation) {
    this.partyAbbreviation = partyAbbreviation;
  }

  public PartyDataDTO partySymbol(String partySymbol) {
    this.partySymbol = partySymbol;
    return this;
  }

  /**
   * Party Symbol String in Base64 format
   * @return partySymbol
   */
  
  @Schema(name = "partySymbol", description = "Party Symbol String in Base64 format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partySymbol")
  public String getPartySymbol() {
    return partySymbol;
  }

  public void setPartySymbol(String partySymbol) {
    this.partySymbol = partySymbol;
  }

  public PartyDataDTO partyFounderName(String partyFounderName) {
    this.partyFounderName = partyFounderName;
    return this;
  }

  /**
   * Party Founder name must be between 2 and 20 characters.
   * @return partyFounderName
   */
  @Size(min = 2, max = 20) 
  @Schema(name = "partyFounderName", example = "John Smith", description = "Party Founder name must be between 2 and 20 characters.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyFounderName")
  public String getPartyFounderName() {
    return partyFounderName;
  }

  public void setPartyFounderName(String partyFounderName) {
    this.partyFounderName = partyFounderName;
  }

  public PartyDataDTO partyFoundationYear(Integer partyFoundationYear) {
    this.partyFoundationYear = partyFoundationYear;
    return this;
  }

  /**
   * Foundation year must be between 1900 and 2025.
   * minimum: 1900
   * maximum: 2025
   * @return partyFoundationYear
   */
  @Min(1900) @Max(2025) 
  @Schema(name = "partyFoundationYear", example = "1998", description = "Foundation year must be between 1900 and 2025.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyFoundationYear")
  public Integer getPartyFoundationYear() {
    return partyFoundationYear;
  }

  public void setPartyFoundationYear(Integer partyFoundationYear) {
    this.partyFoundationYear = partyFoundationYear;
  }

  public PartyDataDTO partyLeaderName(String partyLeaderName) {
    this.partyLeaderName = partyLeaderName;
    return this;
  }

  /**
   * Party Leader name must be between 3 and 20 characters.
   * @return partyLeaderName
   */
  @Size(min = 3, max = 20) 
  @Schema(name = "partyLeaderName", example = "Narendra Modi", description = "Party Leader name must be between 3 and 20 characters.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyLeaderName")
  public String getPartyLeaderName() {
    return partyLeaderName;
  }

  public void setPartyLeaderName(String partyLeaderName) {
    this.partyLeaderName = partyLeaderName;
  }

  public PartyDataDTO partyWebSite(String partyWebSite) {
    this.partyWebSite = partyWebSite;
    return this;
  }

  /**
   * Website URL (Max 200 characters).
   * @return partyWebSite
   */
  @Size(max = 200) 
  @Schema(name = "partyWebSite", example = "https://www.democraticalliance.org", description = "Website URL (Max 200 characters).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyWebSite")
  public String getPartyWebSite() {
    return partyWebSite;
  }

  public void setPartyWebSite(String partyWebSite) {
    this.partyWebSite = partyWebSite;
  }

  public PartyDataDTO headQuarters(String headQuarters) {
    this.headQuarters = headQuarters;
    return this;
  }

  /**
   * Headquarter field must be between 3 and 30 characters.
   * @return headQuarters
   */
  @Size(min = 3, max = 30) 
  @Schema(name = "headQuarters", example = "Washington, D.C.", description = "Headquarter field must be between 3 and 30 characters.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("headQuarters")
  public String getHeadQuarters() {
    return headQuarters;
  }

  public void setHeadQuarters(String headQuarters) {
    this.headQuarters = headQuarters;
  }

  public PartyDataDTO partyId(Long partyId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartyDataDTO partyDataDTO = (PartyDataDTO) o;
    return Objects.equals(this.partyName, partyDataDTO.partyName) &&
        Objects.equals(this.partyAbbreviation, partyDataDTO.partyAbbreviation) &&
        Objects.equals(this.partySymbol, partyDataDTO.partySymbol) &&
        Objects.equals(this.partyFounderName, partyDataDTO.partyFounderName) &&
        Objects.equals(this.partyFoundationYear, partyDataDTO.partyFoundationYear) &&
        Objects.equals(this.partyLeaderName, partyDataDTO.partyLeaderName) &&
        Objects.equals(this.partyWebSite, partyDataDTO.partyWebSite) &&
        Objects.equals(this.headQuarters, partyDataDTO.headQuarters) &&
        Objects.equals(this.partyId, partyDataDTO.partyId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partyName, partyAbbreviation, partySymbol, partyFounderName, partyFoundationYear, partyLeaderName, partyWebSite, headQuarters, partyId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartyDataDTO {\n");
    sb.append("    partyName: ").append(toIndentedString(partyName)).append("\n");
    sb.append("    partyAbbreviation: ").append(toIndentedString(partyAbbreviation)).append("\n");
    sb.append("    partySymbol: ").append(toIndentedString(partySymbol)).append("\n");
    sb.append("    partyFounderName: ").append(toIndentedString(partyFounderName)).append("\n");
    sb.append("    partyFoundationYear: ").append(toIndentedString(partyFoundationYear)).append("\n");
    sb.append("    partyLeaderName: ").append(toIndentedString(partyLeaderName)).append("\n");
    sb.append("    partyWebSite: ").append(toIndentedString(partyWebSite)).append("\n");
    sb.append("    headQuarters: ").append(toIndentedString(headQuarters)).append("\n");
    sb.append("    partyId: ").append(toIndentedString(partyId)).append("\n");
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

