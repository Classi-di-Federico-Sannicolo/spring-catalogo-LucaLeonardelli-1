package com.lucaleonardelli.catalogo_vini.services;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;
import com.lucaleonardelli.catalogo_vini.services.VinoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VinoServiceTest {

    @Mock
    private VinoRepository vinoRepository;

    @InjectMocks
    private VinoService vinoService;

    @Test
    public void testSalvaVino_ForzaturaId_DeveAzzerareIdPrimaDelSalvataggio() {
        UUID idForzato = UUID.randomUUID();
        Vino vinoConIdForzato = new Vino(idForzato, "Nebbiolo", "Cantina Sociale", "Rosso", 2021);

        when(vinoRepository.save(argThat(v -> v.getId() == null))).thenReturn(new Vino(UUID.randomUUID(), "Nebbiolo", "Cantina Sociale", "Rosso", 2021));

        vinoService.salvaVino(vinoConIdForzato);

        verify(vinoRepository).save(argThat(v -> v.getId() == null && "Nebbiolo".equals(v.getNome())));
    }
}