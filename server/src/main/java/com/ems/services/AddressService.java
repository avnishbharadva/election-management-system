package com.ems.services;

import com.ems.dtos.AddressDTO;
import com.ems.entities.Address;
import com.ems.mappers.AddressMapper;
import com.ems.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepo;
    private final AddressMapper addressMapper;

    public AddressDTO save(Address address){
        return addressMapper.toAddressDTO(addressRepo.save(address));
    }
}
