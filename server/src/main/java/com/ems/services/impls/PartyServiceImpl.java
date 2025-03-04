package com.ems.services.impls;

import com.ems.entities.Party;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PartyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final GlobalMapper globalMapper;

    private static final String DIRECTORY = "D:/Spring/election-management-system/server/uploads";
    private static final String PHOTO_DIR = DIRECTORY + "/symbols";


    @Override
    public PartyDTO saveParty(org.openapitools.model.PartyDTO partyDTO) {
        Party party = globalMapper.toParty(partyDTO);
        log.info("Saving party: {}", party.getPartyName());

        createImageDirectory();

        if (partyDTO.getPartySymbol() != null && !partyDTO.getPartySymbol().isEmpty()) {
            try {
                String imgName = handlePartySymbol(party, partyDTO.getPartySymbol());
                party.setPartySymbol(imgName);
                log.info("Party symbol set: {}", imgName);
            } catch (IOException | IllegalArgumentException e) {
                log.error("Error processing party symbol for party: {}", party.getPartyName(), e);
                throw new RuntimeException("Failed to save party symbol", e);
            }
        } else {
            log.warn("Party symbol is null or empty for party: {}", party.getPartyName());
        }

        party = partyRepository.save(party);
        log.info("Party saved successfully with symbol: {}", party.getPartySymbol());
        return globalMapper.toPartyDTO(party);
    }

    private void createImageDirectory() {
        File imageFolder = new File(PHOTO_DIR);
        if (!imageFolder.exists() && !imageFolder.mkdirs()) {
            throw new RuntimeException("Failed to create image directory: " + PHOTO_DIR);
        }
    }

    private String handlePartySymbol(Party party, String base64Symbol) throws IOException {
        if (party.getPartySymbol() != null) {
            File oldImg = new File(PHOTO_DIR, party.getPartySymbol());
            if (oldImg.exists() && !oldImg.delete()) {
                log.warn("Failed to delete old image: {}", oldImg.getAbsolutePath());
            }
        }

        String imgName = party.getPartyName().replaceAll("\\s+", "_") + ".png";
        byte[] imageBytes = Base64.getDecoder().decode(base64Symbol);

        Path imagePath = Paths.get(PHOTO_DIR, imgName);
        Files.write(imagePath, imageBytes);

        return imgName;
    }





//    @Override
//    public PartyDTO partyById(long id) {
//        var party = partyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + id));
//        return globalMapper.toPartyDTO(party);
//    }


//    @Override
//    public List<PartyDTO> findAll() {
//        return partyRepository.findAll().stream().map(globalMapper::toPartyDTO).toList();
//    }
}
