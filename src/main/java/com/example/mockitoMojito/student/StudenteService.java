package com.example.mockitoMojito.student;

import com.example.mockitoMojito.dto.StudenteDTO;
import com.example.mockitoMojito.dto.StudenteDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private StudenteMapper studenteMapper;

    public StudenteDTOResponse getStudenteById(Long id) {
        return studenteMapper.dtoFromStudente(studenteRepository.findById(id).orElse(null));
    }

    public List<StudenteDTOResponse> getAll() {
        return studenteRepository.findAll()
                .stream()
                .map(studenteMapper::dtoFromStudente)
                .toList();
    }

    public StudenteDTOResponse saveStudente(StudenteDTO studenteDTO) {
        Studente studente = studenteMapper.studenteFromDTO(studenteDTO);
        Studente savedStudente = studenteRepository.save(studente);
        return studenteMapper.dtoFromStudente(savedStudente);
    }

    public void deleteById(Long id) {
        studenteRepository.deleteById(id);
    }


}
