package com.ems.mappers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Voter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoterMapper {
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);


    Voter toVoter(VoterSearchDTO voterSearchDTO);
    VoterSearchDTO toVoterSearchDTO(Voter voter);

    Voter updateVoterFromDto(VoterRegisterDTO voterRegisterDTO, @MappingTarget Voter voter);

}
