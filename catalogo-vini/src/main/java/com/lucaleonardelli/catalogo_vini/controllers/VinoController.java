package com.lucaleonardelli.catalogo_vini.controllers;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.dto.VinoDTO;
import com.lucaleonardelli.catalogo_vini.payload.APIResponse;
import com.lucaleonardelli.catalogo_vini.services.VinoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vini")
public class VinoController {

    @Autowired
    private VinoService vinoService;

    private VinoDTO convertToDTO(Vino vino) {
        return new VinoDTO(vino.getId(), vino.getNome(), vino.getCantina(), vino.getCategoria(), vino.getAnno());
    }

    private Vino convertToEntity(VinoDTO dto) {
        return new Vino(dto.getId(), dto.getNome(), dto.getCantina(), dto.getCategoria(), dto.getAnno());
    }

    @GetMapping
    public APIResponse<List<VinoDTO>> getAllVini(
            @RequestParam(name = "ricerca", required = false) String search,
            @RequestParam(name = "sort", required = false, defaultValue = "nome") String sortField) {

        List<Vino> listaVini = vinoService.getAllVini(search, sortField);
        List<VinoDTO> dtos = listaVini.stream().map(this::convertToDTO).collect(Collectors.toList());
        return APIResponse.success(dtos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<VinoDTO> salvaVino(@Valid @RequestBody VinoDTO vinoDTO) {
        Vino vino = convertToEntity(vinoDTO);
        Vino vinoSalvato = vinoService.salvaVino(vino);
        return APIResponse.success(convertToDTO(vinoSalvato));
    }

    @GetMapping("/{id}")
    public APIResponse<VinoDTO> dettaglioVino(@PathVariable("id") UUID id) {
        Vino vino = vinoService.dettaglioVino(id);
        if (vino == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vino non trovato");
        }
        return APIResponse.success(convertToDTO(vino));
    }

    @PutMapping("/{id}")
    public APIResponse<VinoDTO> aggiornaVino(@PathVariable("id") UUID id, @Valid @RequestBody VinoDTO vinoDTO) {
        Vino vino = convertToEntity(vinoDTO);
        Vino vinoAggiornato = vinoService.aggiornaVino(id, vino);
        if (vinoAggiornato == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossibile aggiornare: vino non trovato");
        }
        return APIResponse.success(convertToDTO(vinoAggiornato));
    }

    @DeleteMapping("/{id}")
    public APIResponse<String> eliminaVino(@PathVariable("id") UUID id) {
        boolean eliminato = vinoService.eliminaVino(id);
        if (!eliminato) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossibile eliminare: vino non trovato");
        }
        return APIResponse.success("Vino eliminato con successo dal catalogo!");
    }

    @DeleteMapping("/clear")
    public APIResponse<String> svuotaCatalogo() {
        vinoService.svuotaCatalogo();
        return APIResponse.success("Tutti i vini sono stati eliminati dal catalogo!");
    }
}