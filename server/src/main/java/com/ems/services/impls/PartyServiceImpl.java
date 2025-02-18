package com.ems.services.impls;

import com.ems.dtos.PartyDTO;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
}
