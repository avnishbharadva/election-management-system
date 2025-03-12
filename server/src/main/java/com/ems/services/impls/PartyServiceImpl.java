package com.ems.services.impls;

import com.ems.exceptions.CustomException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.springframework.stereotype.Service;
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
    private static final String UPLOAD_DIR = "uploads/partySymbol";

    @Override
    public PartyDataDTO partyById(long partyId) {
        log.info("party searching for id : {}", partyId);
        var party = partyRepository.findById(partyId).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + partyId));
        return globalMapper.toPartyDTO(party);
    }

    @Override
    public PartyDataDTO saveParty(PartyDataDTO partyDTO) {
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
        party = partyRepository.save(party);
        log.info("Party Successfully Saved : {}", party);
        return globalMapper.toPartyDTO(party);
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
        return partyRepository.findAll().stream().map(globalMapper::toPartyDTO).toList();
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
        log.info("party updated id : {}, data : {}", partyId, party);
        return globalMapper.toPartyDTO(party);
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
}