package com.lucaleonardelli.catalogo_vini.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vini") 
public class Vino {
    
    @Id
    @GeneratedValue 
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cantina")
    private String cantina;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "anno")
    private Integer anno;
}