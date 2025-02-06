package com.ems.mapper;

import com.ems.dtos.VoterDTO;
import com.ems.entities.Voter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoterMapper {

    Voter toVoter(VoterDTO voterDTO);
    VoterDTO toVoterDTO(Voter voter);

}
