package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.repository.PatientRepository;
import br.edu.ifpe.CRMHealthLink.repository.UserRepository;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testPatientPasswordIsEncoded(){
        BCryptPasswordEncoder auxEncoder = new BCryptPasswordEncoder();
        PatientCreateDto patient = new PatientCreateDto("NomeComOito",LocalDate.now(),"12345678910",
                "email",AcessLevel.PATIENT,"password");
        patientService.save(patient);

        verify(passwordEncoder,times(1)).encode(patient.getPassword());
        verify(passwordEncoder,atMostOnce()).encode(anyString());

        verify(patientRepository,atMostOnce()).save(any());
    }
}