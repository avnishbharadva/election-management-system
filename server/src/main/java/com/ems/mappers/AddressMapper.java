package com.ems.mappers;

import com.ems.dtos.AddressDTO;
import com.ems.entities.Address;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressDTO addressDTO);
    AddressDTO toAddressDTO(Address address);
    Set<Address> toAddressSet(Set<AddressDTO> addressDTOS);
}
