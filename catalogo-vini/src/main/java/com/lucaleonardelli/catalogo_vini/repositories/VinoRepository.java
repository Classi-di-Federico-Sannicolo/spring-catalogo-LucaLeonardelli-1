package com.lucaleonardelli.catalogo_vini.repositories;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Sort;

import com.lucaleonardelli.catalogo_vini.domain.Vino;

public interface VinoRepository extends JpaRepository<Vino, UUID> {

    List<Vino> findByNomeContainingIgnoreCase(String nome, Sort sort);
}