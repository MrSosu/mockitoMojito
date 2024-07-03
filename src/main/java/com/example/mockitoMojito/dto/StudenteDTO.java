package com.example.mockitoMojito.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudenteDTO {

    private String nome;
    private String cognome;
    private String email;
    private Integer eta;
    private Long id_university;

}
