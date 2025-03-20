package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * VoterStatusDTO
 */
@lombok.NoArgsConstructor @lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-19T17:13:41.715664500+05:30[Asia/Calcutta]", comments = "Generator version: 7.10.0")
public class VoterStatusDTO {

  private String msg;

  @Valid
  private List<@Valid VoterStatusDataDTO> data = new ArrayList<>();

  public VoterStatusDTO msg(String msg) {
    this.msg = msg;
    return this;
  }

  /**
   * Get msg
   * @return msg
   */
  
  @Schema(name = "msg", example = "Success", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("msg")
  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public VoterStatusDTO data(List<@Valid VoterStatusDataDTO> data) {
    this.data = data;
    return this;
  }

  public VoterStatusDTO addDataItem(VoterStatusDataDTO dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @Valid 
  @Schema(name = "data", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public List<@Valid VoterStatusDataDTO> getData() {
    return data;
  }

  public void setData(List<@Valid VoterStatusDataDTO> data) {
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
    VoterStatusDTO voterStatusDTO = (VoterStatusDTO) o;
    return Objects.equals(this.msg, voterStatusDTO.msg) &&
        Objects.equals(this.data, voterStatusDTO.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(msg, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoterStatusDTO {\n");
    sb.append("    msg: ").append(toIndentedString(msg)).append("\n");
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

