package com.ems.services.impls;

import com.ems.entities.Party;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
//import liquibase.exception.DatabaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PartyDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final GlobalMapper globalMapper;

    private static final String DIRECTORY = "D:/Spring/election-management-system/server/uploads";
    private static final String PHOTO_DIR = DIRECTORY + "/symbols";

    @Override
    public PartyDTO saveParty(PartyDTO partyDTO) {
        Party party = globalMapper.toParty(partyDTO);
        log.info("Saving party: {}", party.getPartyName());

        createImageDirectory();

        if (partyDTO.getPartySymbol() != null && !partyDTO.getPartySymbol().isEmpty()) {
            String imageName = partyDTO.getPartyName().replaceAll("\\s+", "_") + ".png";
            try {
                String imagePath = decodeAndSaveBase64Image(partyDTO.getPartySymbol(), PHOTO_DIR, imageName);

                if(partyDTO.getPartySymbol()!=null)
                    party.setPartySymbol(imagePath);

            } catch (IOException e) {
                log.error("Error saving party symbol for {}: {}", party.getPartyName(), e.getMessage());
                throw new RuntimeException("Failed to save party symbol", e);
            }


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

    private String decodeAndSaveBase64Image(String base64, String directory, String fileName) throws IOException {
        if (base64 == null || base64.isEmpty()) return null;

        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        Path filePath = Paths.get(directory, fileName);
        Files.createDirectories(filePath.getParent()); // Ensure directory exists
        Files.write(filePath, decodedBytes);
        return filePath.toString();
    }

    @Override
    public PartyDTO partyById(long id) {
    var party = partyRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Party not found with id: " + id));
         return globalMapper.toPartyDTO(party);
    }

    @Override
    public List<PartyDTO> findAll() {
        return partyRepository.findAll().stream().map(globalMapper::toPartyDTO).toList();
    }
}
