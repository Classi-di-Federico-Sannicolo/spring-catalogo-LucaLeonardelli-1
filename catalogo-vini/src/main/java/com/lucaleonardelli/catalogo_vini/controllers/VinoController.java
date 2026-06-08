package com.lucaleonardelli.catalogo_vini.controllers;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.dto.VinoDTO;
import com.lucaleonardelli.catalogo_vini.payload.APIResponse;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private VinoRepository vinoRepository;

    // x convertire l'entità in DTO
    private VinoDTO convertToDTO(Vino vino) {
        return new VinoDTO(vino.getId(), vino.getNome(), vino.getCantina(), vino.getCategoria(), vino.getAnno());
    }

    // x covertire il DTO in entità
    private Vino convertToEntity(VinoDTO dto) {
        return new Vino(dto.getId(), dto.getNome(), dto.getCantina(), dto.getCategoria(), dto.getAnno());
    }

    @GetMapping
    public APIResponse<List<VinoDTO>> getAllVini(
            @RequestParam(name = "ricerca", required = false) String search,
            @RequestParam(name = "sort", required = false, defaultValue = "nome") String sortField) {

        Sort ordinamento = Sort.by(sortField).ascending();
        List<Vino> listaVini;

        if (search != null && !search.trim().isEmpty()) {
            listaVini = vinoRepository.findByNomeContainingIgnoreCase(search, ordinamento);
        } else {
            listaVini = vinoRepository.findAll(ordinamento);
        }

        List<VinoDTO> dtos = listaVini.stream().map(this::convertToDTO).collect(Collectors.toList());
        return APIResponse.success(dtos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<VinoDTO> salvaVino(@Valid @RequestBody VinoDTO vinoDTO) {
        Vino vino = convertToEntity(vinoDTO);
        Vino vinoSalvato = vinoRepository.save(vino);
        return APIResponse.success(convertToDTO(vinoSalvato));
    }

    @GetMapping("/{id}")
    public APIResponse<VinoDTO> dettaglioVino(@PathVariable("id") UUID id) {
        Vino vino = vinoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vino non trovato"));
        return APIResponse.success(convertToDTO(vino));
    }

    @PutMapping("/{id}")
    public APIResponse<VinoDTO> aggiornaVino(@PathVariable("id") UUID id, @Valid @RequestBody VinoDTO vinoDTO) {
        Vino vinoEsistente = vinoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossibile aggiornare: vino non trovato"));

        vinoEsistente.setNome(vinoDTO.getNome());
        vinoEsistente.setCantina(vinoDTO.getCantina());
        vinoEsistente.setCategoria(vinoDTO.getCategoria());
        vinoEsistente.setAnno(vinoDTO.getAnno());

        Vino vinoSalva = vinoRepository.save(vinoEsistente);

        return APIResponse.success(convertToDTO(vinoSalva));
    }

    @DeleteMapping("/{id}")
    public APIResponse<String> eliminaVino(@PathVariable("id") UUID id) {
        if (!vinoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossibile eliminare: vino non trovato");
        }
        vinoRepository.deleteById(id);
        return APIResponse.success("Vino eliminato con successo dal catalogo!");
    }

    @DeleteMapping("/clear")
    public APIResponse<String> svuotaCatalogo() {
        vinoRepository.deleteAll();
        return APIResponse.success("Tutti i vini sono stati eliminati dal catalogo!");
    }
}