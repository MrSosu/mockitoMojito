package com.example.mockitoMojito.student;

import com.example.mockitoMojito.dto.StudenteDTO;
import com.example.mockitoMojito.dto.StudenteDTOResponse;
import com.example.mockitoMojito.university.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class StudenteServiceTest {

    @InjectMocks
    private StudenteService studenteService;
    @Mock
    private StudenteMapper studenteMapper;
    @Mock
    private StudenteRepository studenteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDaveStudente() {
        // given
        StudenteDTO studenteDTO = StudenteDTO.builder()
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .id_university(1L)
                .build();

        Studente studente = Studente.builder()
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .university(new University())
                .build();

        Studente savedStudente = Studente.builder()
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .university(new University())
                .build();
        savedStudente.setId(1L);
        // mock delle chiamate
        Mockito.when(studenteMapper.studenteFromDTO(studenteDTO)).thenReturn(studente);
        Mockito.when(studenteRepository.save(studente)).thenReturn(savedStudente);
        Mockito.when(studenteMapper.dtoFromStudente(savedStudente))
                .thenReturn(StudenteDTOResponse.builder()
                        .id(1L)
                        .nome("Homer")
                        .cognome("Simpson")
                        .eta(40)
                        .email("flanders@mail.com")
                        .build()
                );
        // when
        StudenteDTOResponse studenteDTOResponse = studenteService.saveStudente(studenteDTO);

        // then
        Assertions.assertEquals(studenteDTO.getNome(), studenteDTOResponse.getNome());
        Assertions.assertEquals(studenteDTO.getCognome(), studenteDTOResponse.getCognome());
        Assertions.assertEquals(studenteDTO.getEmail(), studenteDTOResponse.getEmail());
        Assertions.assertEquals(studenteDTO.getEta(), studenteDTOResponse.getEta());

        // ora voglio verificare che l'inserimento sia avvenuto una sola volta
        Mockito.verify(studenteMapper, Mockito.times(1)).studenteFromDTO(studenteDTO);
        Mockito.verify(studenteRepository, Mockito.times(1)).save(studente);
        Mockito.verify(studenteMapper, Mockito.times(1)).dtoFromStudente(savedStudente);
    }
}