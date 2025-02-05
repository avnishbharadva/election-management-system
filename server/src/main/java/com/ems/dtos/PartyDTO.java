package com.ems.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PartyDTO {

    private Long partyId;

    @NotBlank(message = "Party name cannot be blank")
    @Size(max = 255, message = "Party name is too long")
    private String partyName;

    @NotBlank(message = "Party abbreviation cannot be blank")
    @Size(max = 10, message = "Party abbreviation is too long")
    private String partyAbbreviation;

    private List<Long> candidateIds;
}
