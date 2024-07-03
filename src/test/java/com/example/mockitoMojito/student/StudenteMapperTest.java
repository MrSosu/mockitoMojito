package com.example.mockitoMojito.student;

import com.example.mockitoMojito.dto.StudenteDTO;
import com.example.mockitoMojito.dto.StudenteDTOResponse;
import com.example.mockitoMojito.university.University;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudenteMapperTest {

    /*
    @BeforeAll
    static void beforeAll() {
        System.out.println("stampa questo e poi tutto il resto");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("metodo dopo tutto, pure la morte");
    }

    @BeforeEach
    void setUp() {
        System.out.println("sto prima di ogni metodo EHEHEHEHEH");
    }

    @AfterEach
    void tearDown() {
        System.out.println("sto alla fine di ogni metodo YOHOHOHOHO!");
    }

    @Test
    void metodo1() {
        System.out.println("ciao sono il metodo 1! Forza Roma!");
    }

    @Test
    void metodo2() {
        System.out.println("ciao sono il metodo 2! W quella cosa che finisce con no!");
    } */

    private StudenteMapper studenteMapper;

    @BeforeEach
    void setUp() {
        studenteMapper = new StudenteMapper();
    }

    @Test
    public void testMapDTOToStudente() {
        StudenteDTO studenteDTO = StudenteDTO.builder()
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .id_university(1L)
                .build();
        Studente studente = studenteMapper.studenteFromDTO(studenteDTO);
        Assertions.assertEquals(studenteDTO.getNome(), studente.getNome());
        Assertions.assertEquals(studenteDTO.getCognome(), studente.getCognome());
        Assertions.assertEquals(studenteDTO.getEmail(), studente.getEmail());
        Assertions.assertEquals(studenteDTO.getEta(), studente.getEta());
        Assertions.assertNotNull(studente.getUniversity());
        Assertions.assertEquals(studenteDTO.getId_university(), studente.getUniversity().getId());
    }

    @Test
    public void testMapDTOToStudenteIfNull() {
        var exp = Assertions.assertThrows(NullPointerException.class,
                () -> studenteMapper.studenteFromDTO(null));
        Assertions.assertEquals(exp.getMessage(), "Il DTO dello studente è null");
    }

    @Test
    public void testMapFromStudentToDTO() {
        // given
        Studente studente = Studente.builder()
                .id(1L)
                .nome("Homer")
                .cognome("Simpson")
                .eta(40)
                .email("flanders@mail.com")
                .university(new University())
                .build();
        // when
        StudenteDTOResponse studenteDTO = studenteMapper.dtoFromStudente(studente);
        // then
        Assertions.assertEquals(studenteDTO.getNome(), studente.getNome());
        Assertions.assertEquals(studenteDTO.getCognome(), studente.getCognome());
        Assertions.assertEquals(studenteDTO.getEmail(), studente.getEmail());
        Assertions.assertEquals(studenteDTO.getEta(), studente.getEta());
    }

}