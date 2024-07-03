package com.example.mockitoMojito.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudenteDTOResponse {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private Integer eta;

}
