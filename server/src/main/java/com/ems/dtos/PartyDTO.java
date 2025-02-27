package com.ems.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PartyDTO {

    private Long partyId;

    @NotBlank(message = "Party name can not be blank")
    @Size(min = 3, max = 30, message = "Party Name must be between 2 and 50 characters")
    private String partyName;

    @NotBlank(message = "Party Abbreviation can not be blank")
    @Size(max = 10, message = "Party Abbreviation must not exceed 10 characters")
    private String partyAbbreviation;

    private MultipartFile partySymbol;

    @NotNull(message = "Foundation year can not be null")
    private Integer partyFoundationYear;

    @Column(length = 200, name = "Website name must not exceed 200 characters")
    private String partyWebSite;

    @NotBlank(message = "Headquarter can not be blank")
    private String headQuarters;

    @NotBlank(message = "Founder name can not be blank")
    private String founderName;
}
