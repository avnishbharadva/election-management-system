package com.ems.controllers;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import com.ems.services.PartyService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/api/party")
public class PartyController {
    private final PartyService partyService;

    @PostMapping("/newParty")
    Party createParty(@RequestBody PartyDTO partyDTO)
    {
        return partyService.saveParty(partyDTO);
    }

}
