package com.ems.controllers;
import com.ems.dtos.*;
import com.ems.entities.Election;
import com.ems.services.ElectionService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@RestController
@Data
@RequestMapping("/api/elections")
public class ElectionController {
    private final ElectionService electionService;
    @PostMapping("/addElection")
    Election createElection(@RequestBody ElectionDTO electionDTO){
        return electionService.saveElection(electionDTO);
    }
    @PutMapping("/update/{electionId}")
    Election updateElection(@PathVariable Long electionId,@Valid  @RequestBody ElectionDTO electionDTO)
    {
        return electionService.updateElection(electionId,electionDTO);

    }

   @GetMapping("/sorted")
    public ResponseEntity<ElectionPageResponse> getSortedElections(
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page,  // Page index should start from 0
            @RequestParam(name="size",defaultValue = "5") int size) {
 
        ElectionPageResponse response = electionService.getElectionsSorted(order, page, size);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{electionId}")
    public ResponseEntity<ErrorResponse> deleteById(@PathVariable Long electionId) {
        electionService.deleteElectionById(electionId);
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage("Election with id:"+electionId+" is deleted");
        errorResponse.setRequestTime(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(errorResponse);
    }

    @GetMapping("/getAllElection")
    public ResponseEntity<ResponseDTO> getAllElection() {
        List<ElectionDTO> electionDetailsList = electionService.getAllElection();
        if (electionDetailsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("No Election details found", Collections.emptyList(), LocalDateTime.now(), false));
        }
        return ResponseEntity.ok(
                new ResponseDTO("Election details retrieved successfully", electionDetailsList, LocalDateTime.now(), true)
        );

    }

}

