package com.ems.mappers;

import com.ems.dtos.*;
import com.ems.entities.*;
import com.ems.entities.constants.AddressType;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(target = "voterStatus", ignore = true)
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    Voter toVoter(VoterSearchDTO voterSearchDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "address", ignore = true) // Ignore address field in direct mapping
    Voter voterDTOtoVoter(VoterDTO voterDTO, @MappingTarget Voter voter);

//    @AfterMapping
//    default void mapAddresses(VoterDTO voterDTO, @MappingTarget Voter voter) {
//
//        List<Address> addresses = new ArrayList<>();
//
//        if (voterDTO.getResidentialAddress() != null) {
//            Address residentialAddress = mapAddressDTO(voterDTO.getResidentialAddress(), voter);
//            residentialAddress.setAddressType(AddressType.RESIDENTIAL);
//            addresses.add(residentialAddress);
//        }
//
//        if (voterDTO.getMailingAddress() != null) {
//            Address mailingAddress = mapAddressDTO(voterDTO.getMailingAddress(), voter);
//            mailingAddress.setAddressType(AddressType.MAILING);
//            addresses.add(mailingAddress);
//        }
//
//        voter.setAddress(addresses);
//    }

//    default Address mapAddressDTO(AddressDTO addressDTO, Voter voter) {
//        Address address = new Address();
//        address.setAddressLine(addressDTO.getAddressLine());
//        address.setAptNumber(addressDTO.getAptNumber());
//        address.setCity(addressDTO.getCity());
//        address.setCounty(addressDTO.getCounty());
//        address.setState(addressDTO.getState());
//        address.setZipCode(addressDTO.getZipCode());
//        address.setVoter(voter);
//        return address;
//    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address addressDTOToAddress(AddressDTO addressDTO, @MappingTarget Address address);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);

    @Mapping(target = "residentialAddress",
            expression = "java(getAddressByType(voter.getAddress(), com.ems.entities.constants.AddressType.RESIDENTIAL))")
    @Mapping(target = "mailingAddress",
            expression = "java(getAddressByType(voter.getAddress(), com.ems.entities.constants.AddressType.MAILING))")
    @Mapping(source = "party.partyId", target = "partyId")
    @Mapping(target = "statusId", source = "voterStatus.statusId")
    VoterDTO toVoterDTO(Voter voter);

    @Mapping(target = "partySymbol", ignore = true)
    Party toParty(PartyDTO partyDTO);
    @Mapping(target = "partySymbol", ignore = true)
    PartyDTO toPartyDTO(Party party);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "voter", ignore = true)
    Address toAddress(AddressDTO addressDTO);

    @Mapping(source = "voter.voterId", target = "voterId")
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);


    Election toElectionDTO(ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);

    List<VoterStatusDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList);

    @Mapping(source = "address.voter.voterId", target = "voterId")
    AddressHistory toAddressHistory(Address address);

    NameHistory toNameHistory(Voter voter);

    default AddressDTO getAddressByType(List<Address> addresses, AddressType type) {
        if (addresses == null) return null;
        return addresses.stream()
                .filter(address -> address.getAddressType() == type)
                .findFirst()
                .map(this::toAddressDTO)
                .orElse(null);
    }
    Role toRole(RoleRegisterDTO roleRegisterDTO);
    RoleRegisterDTO toRoleRegisterDTO(Role role);
    List<RoleResponseDTO> toRoleResponseDTO(List<Role> role);
}
