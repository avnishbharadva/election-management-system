    package com.ems.controllers;

    import com.ems.dtos.ElectionDTO;
    import com.ems.dtos.ElectionSortDTO;
    import com.ems.entities.Election;
    import com.ems.services.ElectionService;
    import jakarta.validation.Valid;
    import lombok.Data;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

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
        public List<ElectionSortDTO> getSortedElections(@RequestParam(defaultValue = "asc") String order) {
            return electionService.getElectionsSorted(order);
        }
        @DeleteMapping("/delete/{electionId}")
        public ResponseEntity<String> deleteById(@PathVariable Long electionId) {
            electionService.deleteElectionById(electionId);
            return ResponseEntity.ok("Election with id: " + electionId + " deleted successfully");
        }


    }
