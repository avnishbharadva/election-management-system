package com.ems.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PartyDTO {

    private Long partyId;

    @Size(max = 255, message = "Party name is too long")
    private String partyName;

    @Size(max = 10, message = "Party abbreviation is too long")
    private String partyAbbreviation;

    @Lob
    @Column(columnDefinition = "BLOB")
    private Byte[] partySymbol;

    private int partyFoundationYear;

    private String partyWebSite;

    private String headQuarters;

    private String founderName;

    private List<Long> candidateIds;

}
