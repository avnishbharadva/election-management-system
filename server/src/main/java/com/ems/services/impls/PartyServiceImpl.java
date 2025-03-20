package com.ems.services.impls;

<<<<<<< HEAD
import com.ems.entities.Party;
=======
import com.ems.exceptions.CustomException;
import com.ems.exceptions.DataAlreadyExistException;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD
import org.openapitools.model.PartyDTO;
import org.springframework.stereotype.Service;

import java.io.File;
=======
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.springframework.stereotype.Service;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
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
<<<<<<< HEAD

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
        var party = partyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + id));
=======
    private static final String UPLOAD_DIR = "uploads/partySymbol";

    @Override
    public PartyDataDTO partyById(long partyId) {
        log.info("party searching for id : {}", partyId);
        var party = partyRepository.findById(partyId).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + partyId));
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
        return globalMapper.toPartyDTO(party);
    }

    @Override
<<<<<<< HEAD
    public List<PartyDTO> findAll() {
        return partyRepository.findAll().stream().map(globalMapper::toPartyDTO).toList();
    }
}
=======
    public PartyDataDTO saveParty(PartyRegisterDTO partyDTO) {
        log.info("party registration for : {},{},{},{},{},{},{}", partyDTO.getPartyName(),partyDTO.getPartyAbbreviation(),partyDTO.getPartyFounderName(),partyDTO.getPartyFoundationYear(),partyDTO.getPartyLeaderName(),partyDTO.getPartyWebSite(),partyDTO.getHeadQuarters());

        if(partyRepository.existsByPartyNameOrPartyAbbreviationOrPartyLeaderName(partyDTO.getPartyName(),partyDTO.getPartyAbbreviation(), partyDTO.getPartyLeaderName()))
            throw new DataAlreadyExistException("Party with name{"+partyDTO.getPartyName()+"}, abbreviation{"+partyDTO.getPartyAbbreviation()+"} or leader name{"+partyDTO.getPartyLeaderName()+"} already exists");

        var party = globalMapper.toParty(partyDTO);
        var partySymbol = party.getPartySymbol();

        String extension = extractExtension(partySymbol);
        if (extension == null) {
            throw new CustomException("Invalid Base64 image format");
        }
        String fileName = party.getPartyName() + "." + extension;
        String pureBase64 = partySymbol.contains(",") ? partySymbol.split(",")[1] : partySymbol;
        byte[] decodedBytes = Base64.getDecoder().decode(pureBase64);
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        log.info("Filepath : {} of party symbol for party : {}",filePath,party.getPartyName());
        try {
            log.info("get parent for party symbol to create directory : {}", filePath.getParent());
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, decodedBytes);
        } catch (IOException e) {
            throw new CustomException("Failed to save party symbol: " + e.getMessage());
        }
        party.setPartySymbol(fileName);
        partyRepository.save(party);
        var partyResponse = globalMapper.toPartyDTO(party);
        Path imagePath = Path.of(UPLOAD_DIR + "/" + partyResponse.getPartySymbol());
        partyResponse.setPartySymbol(encodeFileToBase64(imagePath));
        log.info("Party Successfully Saved : {}", partyResponse);
        return partyResponse;
    }

    private String extractExtension(String base64) {
        if (base64.startsWith("data:image/")) {
            String[] parts = base64.split(";")[0].split("/");
            return parts.length > 1 ? parts[1] : null;
        }
        return null;
    }

    @Override
    public List<PartyDataDTO> findAll() {
        var parties = partyRepository.findAll();
        parties.forEach(party -> party.setPartySymbol(encodeFileToBase64(Path.of(UPLOAD_DIR + "/" + party.getPartySymbol()))));
        return parties.stream().map(globalMapper::toPartyDTO).toList();
    }

    @Override
    public PartyDataDTO updateParty(Long partyId, PartyUpdateDTO partyUpdateDTO) {
        log.info("Party update call for id : {}, data : {}",partyId,partyUpdateDTO);

        var party = partyRepository.findById(partyId)
                .orElseThrow(() -> new DataNotFoundException("No Party Found For Party ID : " + partyId));

        var oldPartySymbol = party.getPartySymbol();
        globalMapper.partyUpdateDTOToParty(partyUpdateDTO, party);

        if (partyUpdateDTO.getPartySymbol() != null && !partyUpdateDTO.getPartySymbol().isEmpty()) {
            String newBase64 = partyUpdateDTO.getPartySymbol();
            String extension = extractExtension(newBase64);
            if (extension == null) {
                throw new CustomException("Invalid Base64 image format");
            }
            String fileName = party.getPartyName() + "." + extension;
            String pureBase64 = newBase64.contains(",") ? newBase64.split(",")[1] : newBase64;
            byte[] decodedBytes = Base64.getDecoder().decode(pureBase64);
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            try {
                deleteExistingFile(oldPartySymbol);
                Files.write(filePath, decodedBytes);
            } catch (IOException e) {
                throw new CustomException("Failed to save party symbol: " + e.getMessage());
            }
            party.setPartySymbol(fileName);
        }

        partyRepository.save(party);
        var partyResponse = globalMapper.toPartyDTO(party);
        Path imagePath = Path.of(UPLOAD_DIR + "/" + partyResponse.getPartySymbol());
        partyResponse.setPartySymbol(encodeFileToBase64(imagePath));
        log.info("party updated id : {}, data : {}", partyId, party);
        return partyResponse;
    }

    @Override
    public void deleteParty(Long partyId) {
        log.info("party delete start for id : {}", partyId);

        var party = partyRepository.findById(partyId).orElseThrow(() -> new DataNotFoundException("Party with id{" + partyId + "} not exists"));

        partyRepository.deleteById(partyId);
        deleteExistingFile(party.getPartySymbol());
        log.info("party successfully deleted : {}",partyId);
    }

    private void deleteExistingFile(String fileName) {
        if (fileName == null || fileName.isBlank())
            return;

        try {
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            if (Files.exists(filePath)) {
                Files.deleteIfExists(filePath);
                log.info("Deleted old party symbol file: {}", filePath);
            }
        } catch (IOException e) {
            log.warn("Failed to delete old party symbol file: {}. Reason: {}", fileName, e.getMessage());
        }
    }

    private String encodeFileToBase64(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                log.info("{} : Encoded file to Base64", filePath.getFileName());
                return encodedString;
            } else {
                log.info("File does not exist at path: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Error encoding file to Base64 at path: {}", filePath, e);
        }
        return null;
    }
}
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
