package com.ems.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class CandidateAddress {

    @Id
    @GeneratedValue
    private Long addressId;

    private String street;
    private String city;
    private Integer zipcode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateAddress that = (CandidateAddress) o;
        return zipcode == that.zipcode &&
                Objects.equals(street, that.street) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, street, city, zipcode);
    }
}
