package com.ems.services.impls;

import com.ems.entities.*;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.MaritalStatus;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private GlobalMapper globalMapper;

    @Mock
    private CandidateMapper candidateMapper;

    @Mock
    private ElectionRepository electionRepository;

    private Candidate candidate;
    private CandidateAddress candidateAddress;
    private BankDetails bankDetails;
    private Election election;

    @BeforeEach
    void setUp() {

        CandidateName candidateName = new CandidateName();
        candidateName.setFirstName("John");
        candidateName.setMiddleName("A.");
        candidateName.setLastName("Doe");

        candidateAddress = new CandidateAddress();
        candidateAddress.setAddressId(1001L);
        candidateAddress.setStreet("123 Main St");
        candidateAddress.setCity("New York");
        candidateAddress.setZipcode(10001);

        bankDetails = new BankDetails();
        bankDetails.setBankDetailsId(2001L);
        bankDetails.setBankName("XYZ Bank");
        bankDetails.setBankAddress("456 Bank St, NY");

        election = new Election();
        election.setElectionId(3001L);
        election.setElectionName("Presidential Election 2025");
        election.setElectionType("National");
        election.setElectionDate(LocalDate.of(2025, 11, 8));
        election.setElectionState("New York");
        election.setTotalSeats(100);

        candidate = new Candidate();
        candidate.setCandidateId(2001L);
        candidate.setCandidateName(candidateName);
        candidate.setCandidateSSN("123456789");
        candidate.setDateOfBirth(LocalDate.of(1985, 5, 20));
        candidate.setGender(Gender.MALE);
        candidate.setCandidateEmail("john.doe@example.com");
        candidate.setMaritalStatus(MaritalStatus.MARRIED);
        candidate.setNoOfChildren(2);
        candidate.setSpouseName("Jane Doe");
        candidate.setStateName("New York");
        candidate.setCandidateSignature("JohnDoeSignature");
        candidate.setResidentialAddress(candidateAddress);
        candidate.setMailingAddress(candidateAddress);
        candidate.setBankDetails(bankDetails);
        candidate.setElection(election);
    }


}
