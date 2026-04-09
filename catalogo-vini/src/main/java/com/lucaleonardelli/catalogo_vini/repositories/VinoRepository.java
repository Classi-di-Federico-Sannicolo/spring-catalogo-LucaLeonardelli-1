package com.lucaleonardelli.catalogo_vini.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucaleonardelli.catalogo_vini.domain.Vino;

public interface VinoRepository extends JpaRepository<Vino, UUID> {
}