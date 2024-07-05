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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void testSaveStudente() {
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

    @Test
    public void testGetById() {
        // given
        Long id = 1L;
        Studente studente = Studente.builder()
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .university(new University())
                .build();

        StudenteDTOResponse studenteDTOResponse = StudenteDTOResponse.builder()
                .id(id)
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .build();

        // mock delle chiamate
        Mockito.when(studenteRepository.findById(id)).thenReturn(Optional.of(studente));
        Mockito.when(studenteMapper.dtoFromStudente(studente)).thenReturn(studenteDTOResponse);

        // when
        StudenteDTOResponse result = studenteService.getStudenteById(id);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getNome(), studenteDTOResponse.getNome());
        Assertions.assertEquals(result.getCognome(), studenteDTOResponse.getCognome());
        Assertions.assertEquals(result.getEmail(), studenteDTOResponse.getEmail());
        Assertions.assertEquals(result.getEta(), studenteDTOResponse.getEta());

        Mockito.verify(studenteMapper, Mockito.times(1)).dtoFromStudente(studente);
        Mockito.verify(studenteRepository, Mockito.times(1)).findById(id);

    }

    @Test
    public void testGetAll() {
        // given
        List<Studente> students = new ArrayList<>();
        Studente studente = Studente.builder()
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .university(new University())
                .build();
        Studente anotherStudente = Studente.builder()
                .nome("Peter")
                .cognome("Griffin")
                .eta(44)
                .email("peter@gmail.com")
                .university(new University())
                .build();

        students.add(studente);
        students.add(anotherStudente);

        // mock delle chiamate
        Mockito.when(studenteRepository.findAll()).thenReturn(students);
        Mockito.when(studenteMapper.dtoFromStudente(Mockito.any(Studente.class)))
                .thenAnswer(invocationOnMock -> {
                    Studente result = invocationOnMock.getArgument(0);
                    return StudenteDTOResponse.builder()
                            .id(result.getId())
                            .nome(result.getNome())
                            .cognome(result.getCognome())
                            .email(result.getEmail())
                            .eta(result.getEta())
                            .build();
                });

        // when
        List<StudenteDTOResponse> result = studenteService.getAll();

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.getFirst().getNome(), "Homer");
        Assertions.assertEquals(result.getFirst().getCognome(), "Simpson");
        Assertions.assertEquals(result.get(0).getEta(), 40);
        Assertions.assertEquals(result.get(0).getEmail(), "flanders@mail.com");

        Assertions.assertEquals(result.get(1).getNome(), "Peter");
        Assertions.assertEquals(result.get(1).getCognome(), "Griffin");
        Assertions.assertEquals(result.get(1).getEta(), 44);
        Assertions.assertEquals(result.get(1).getEmail(), "peter@gmail.com");

        Mockito.verify(studenteRepository, Mockito.times(1)).findAll();
        Mockito.verify(studenteMapper, Mockito.times(2)).dtoFromStudente(Mockito.any(Studente.class));

    }

    @Test
    public void testDeleteById() {
        // given
        Long idStudente = 1L;

        // when
        studenteService.deleteById(idStudente);

        // then
        Assertions.assertNull(studenteService.getStudenteById(idStudente));
        Mockito.verify(studenteRepository, Mockito.times(1)).deleteById(idStudente);
    }

}