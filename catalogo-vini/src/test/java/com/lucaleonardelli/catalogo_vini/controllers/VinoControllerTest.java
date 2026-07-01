package com.lucaleonardelli.catalogo_vini.controllers;

import com.lucaleonardelli.catalogo_vini.controllers.VinoController;
import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.services.VinoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VinoController.class)
public class VinoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VinoService vinoService;

    @Test
    public void testDettaglioVino_Successo_DeveRestituireStrutturaStandard() throws Exception {
        UUID idValido = UUID.randomUUID();
        Vino vinoMock = new Vino(idValido, "Barolo", "Marchesi", "Rosso", 2019);
        
        when(vinoService.dettaglioVino(idValido)).thenReturn(vinoMock);

        mockMvc.perform(get("/api/vini/" + idValido)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Validazione nodi JSON come da richiesta della Task 4
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").value(idValido.toString()))
                .andExpect(jsonPath("$.data.nome").value("Barolo"))
                .andExpect(jsonPath("$.data.cantina").value("Marchesi"));
    }

    @Test
    public void testDettaglioVino_RisorsaNonTrovata_DeveRispondere404() throws Exception {
        UUID idInesistente = UUID.randomUUID();
        when(vinoService.dettaglioVino(idInesistente)).thenReturn(null);

        mockMvc.perform(get("/api/vini/" + idInesistente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}