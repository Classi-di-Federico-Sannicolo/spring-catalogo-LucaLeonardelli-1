package com.lucaleonardelli.catalogo_vini.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VinoDTO {
    
    private UUID id;

    @NotBlank(message = "Il nome del vino è obbligatorio")
    private String nome;

    @NotBlank(message = "La cantina è obbligatoria")
    private String cantina;

    @NotBlank(message = "La categoria è obbligatoria")
    private String categoria;

    @NotNull(message = "L'anno è obbligatorio")
    @Min(value = 1000, message = "L'anno non è valido")
    private Integer anno;
}