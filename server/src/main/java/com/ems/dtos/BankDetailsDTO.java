package com.ems.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BankDetailsDTO {

    private Long bankDetailsId;

    @NotBlank(message = "Bank name cannot be blank")
    @Size(max = 255, message = "Bank name is too long")
    private String bankName;

    @NotBlank(message = "Bank address cannot be blank")
    @Size(max = 500, message = "Bank address is too long")
    private String bankAddress;

    private List<Long> candidateIds; // Store only candidate IDs instead of full objects
}
