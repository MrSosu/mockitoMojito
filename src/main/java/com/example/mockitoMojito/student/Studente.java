package com.example.mockitoMojito.student;

import com.example.mockitoMojito.university.University;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Studente {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Integer eta;
    private University university;

}
