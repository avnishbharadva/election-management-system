package com.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class BankDetails {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "bank_details_id", sequenceName = "bank_details_sequence", allocationSize = 1, initialValue = 10)

    private Long bankDetailsId;

    private String bankName;

    private String bankAddress;
}
