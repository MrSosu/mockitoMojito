package com.example.mockitoMojito.student;

import com.example.mockitoMojito.dto.StudenteDTO;
import com.example.mockitoMojito.dto.StudenteDTOResponse;
import com.example.mockitoMojito.university.University;
import org.springframework.stereotype.Service;

@Service
public class StudenteMapper {

    public Studente studenteFromDTO(StudenteDTO studenteDTO) {
        if (studenteDTO == null) throw new NullPointerException("Il DTO dello studente è null");
        var university = new University();
        university.setId(studenteDTO.getId_university());
        return Studente.builder()
                .nome(studenteDTO.getNome())
                .cognome(studenteDTO.getCognome())
                .email(studenteDTO.getEmail())
                .eta(studenteDTO.getEta())
                .university(university)
                .build();
    }

    public StudenteDTOResponse dtoFromStudente(Studente studente) {
        return StudenteDTOResponse.builder()
                .id(studente.getId())
                .nome(studente.getNome())
                .cognome(studente.getCognome())
                .email(studente.getEmail())
                .eta(studente.getEta())
                .build();
    }

}
