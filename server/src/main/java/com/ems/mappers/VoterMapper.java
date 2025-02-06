package com.ems.mappers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.entities.Voter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoterMapper {
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);
}
