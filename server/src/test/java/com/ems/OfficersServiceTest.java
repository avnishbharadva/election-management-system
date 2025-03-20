package com.ems;

import com.ems.entities.Officers;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CountyRepository;
import com.ems.repositories.OfficersRepository;
import com.ems.services.impls.OfficersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfficersServiceTest {

    @Mock
    private OfficersRepository officersRepository;

    @Mock
    private CountyRepository countyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private OfficersServiceImpl officersService;

    private OfficersRegisterDTO validOfficer;
    private Officers officer;

    @BeforeEach
    void setUp() {
        validOfficer = new OfficersRegisterDTO();
        validOfficer.setSsnNumber("123456789");
        validOfficer.setRole("COUNTY");
        validOfficer.setEmail("officer@example.com");
        validOfficer.setPassword("SecurePass123");
        validOfficer.setFirstName("John");
        validOfficer.setMiddleName("A.");
        validOfficer.setLastName("Doe");
        validOfficer.setCounty("Orange County");

        officer = new Officers();
        officer.setEmail(validOfficer.getEmail());
        officer.setCountyName(validOfficer.getCounty());
        officer.setSsnNumber(validOfficer.getSsnNumber());
    }

    @Test
    void createRole_Success() {
        when(countyRepository.existsByCountyName(validOfficer.getCounty())).thenReturn(true);
        when(officersRepository.existsByEmailOrCountyNameOrSsnNumber(validOfficer.getEmail(),validOfficer.getCounty(), validOfficer.getSsnNumber())).thenReturn(false);
        when(globalMapper.toOfficer(validOfficer)).thenReturn(officer);
        when(officersRepository.save(any(Officers.class))).thenReturn(officer);
        when(globalMapper.toOfficerResponseDTO(any(Officers.class))).thenReturn(new OfficersResponseDTO());

        assertDoesNotThrow(() ->  officersService.createRole(validOfficer));
        assertInstanceOf(OfficersResponseDTO.class, officersService.createRole(validOfficer));
        verify(officersRepository, times(2)).save(any());
    }

    @Test
    void countyDoesNotExist() {
        when(countyRepository.existsByCountyName(validOfficer.getCounty())).thenReturn(false);
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> officersService.createRole(validOfficer));
        assertEquals("County 'Orange County' does not exist.", exception.getMessage());
        verify(officersRepository, never()).save(any());
    }

    @Test
    void officerAlreadyExists() {
        when(countyRepository.existsByCountyName(validOfficer.getCounty())).thenReturn(true);
        when(officersRepository.existsByEmailOrCountyNameOrSsnNumber(any(), any(), any())).thenReturn(true);
        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class,
                () -> officersService.createRole(validOfficer));
        assertEquals("Officer with this Email, SSN, or County already exists.", exception.getMessage());
        verify(officersRepository, never()).save(any());
    }

    @Test
    void PasswordEncodingFails() {
        when(countyRepository.existsByCountyName(validOfficer.getCounty())).thenReturn(true);
        when(officersRepository.existsByEmailOrCountyNameOrSsnNumber(validOfficer.getEmail(), validOfficer.getCounty(), validOfficer.getSsnNumber())).thenReturn(false);
        when(passwordEncoder.encode(validOfficer.getPassword())).thenThrow(new RuntimeException("Role creation failed"));
        IllegalCredentials exception = assertThrows(IllegalCredentials.class,
                () -> officersService.createRole(validOfficer));
        assertEquals("Role creation failed", exception.getMessage());
    }

    @Test
    void DatabaseSaveFails() {
        when(countyRepository.existsByCountyName(validOfficer.getCounty())).thenReturn(true);
        when(officersRepository.existsByEmailOrCountyNameOrSsnNumber(validOfficer.getEmail(), validOfficer.getCounty(), validOfficer.getSsnNumber())).thenReturn(false);
        when(passwordEncoder.encode(validOfficer.getPassword())).thenReturn("encodedPassword");
        when(globalMapper.toOfficer(validOfficer)).thenReturn(officer);
        when(officersRepository.save(any(Officers.class))).thenThrow(new IllegalCredentials("Role creation failed"));
        IllegalCredentials exception = assertThrows(IllegalCredentials.class,
                () -> officersService.createRole(validOfficer));
        assertEquals("Role creation failed", exception.getMessage());
    }
}
