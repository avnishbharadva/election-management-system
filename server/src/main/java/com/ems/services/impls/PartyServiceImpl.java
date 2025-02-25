package com.ems.services.impls;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final GlobalMapper globalMapper;

    private static final String PHOTO_DIR = "E:/Internship/Election_Management_System/election-management-system/server/uploads";

    @Override
    public PartyDTO partyById(long id) {
        var party = partyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + id));
        return globalMapper.toPartyDTO(party);
    }

    @Override
    public PartyDTO saveParty(PartyDTO partyDTO, MultipartFile image) throws IOException {
        var party = globalMapper.toParty(partyDTO);

        log.info("Party Successfully Saved : {}", party.getPartyName());

        String imageDir = "uploads/partySymbol";
        File imageFolder = new File(imageDir);

        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        if (image != null && !image.isEmpty()) {
            if (party.getPartySymbol() != null) {
                File oldImg = new File(imageDir, party.getPartySymbol());
                if (oldImg.exists()) {
                    oldImg.delete();
                }
            }

            String imgName = party.getPartyName() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(imageDir, imgName);
            Files.write(imagePath, image.getBytes());
            party.setPartySymbol(imgName);
            party = partyRepository.save(party);

        }
        return globalMapper.toPartyDTO(party);

    }

    @Override
    public List<PartyDTO> findAll() {
        return partyRepository.findAll().stream().map(globalMapper::toPartyDTO).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PartyDTO updateParty(Long partyId, PartyDTO partyDTO, MultipartFile symbolImg) throws IOException {
        log.info("Party Update Service Method...");

        var existingParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new DataNotFoundException("No Party Found From Party ID : " + partyId));

        if (partyDTO == null) {
            updateSymbolImage(existingParty, symbolImg);
            log.info("Updated Only Party Symbol for party with ID: {}", partyId);
            return globalMapper.toPartyDTO(existingParty);
        }

        partyDTO.setPartyId(partyId);
        var updatedParty = globalMapper.toPartyUpdate(partyDTO, existingParty);
        log.info("Updated party details for ID: {}", partyId);
        updateSymbolImage(updatedParty, symbolImg);

        return globalMapper.toPartyDTOUpdate(updatedParty, partyDTO);
    }


    private void updateSymbolImage(Party updatedParty, MultipartFile symbolImg) throws IOException {
        if (symbolImg == null || symbolImg.isEmpty()) return;

        deleteExistingFile(PHOTO_DIR, updatedParty.getPartySymbol());

        String imgName = "Party Id " + updatedParty.getPartyId() + "_" + symbolImg.getOriginalFilename();
        Path imagePath = Paths.get(PHOTO_DIR, imgName);
        Files.write(imagePath, symbolImg.getBytes());

        updatedParty.setPartySymbol(imgName);
    }

    private void deleteExistingFile(String directory, String fileName) {
        if (fileName == null || fileName.isBlank()) return;

        try {
            Path filePath = Paths.get(directory, fileName);
            if (Files.exists(filePath)) {
                Files.deleteIfExists(filePath);
                log.info("Deleted old file: {}", filePath);
            }
        } catch (IOException e) {
            log.warn("Failed to delete file: {}. Reason: {}", fileName, e.getMessage());


        }

    }
}
