package com.lucaleonardelli.catalogo_vini.services;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VinoService {

    @Autowired
    private VinoRepository vinoRepository;

    public List<Vino> getAllVini(String search, String sortField) {
        Sort ordinamento = Sort.by(sortField).ascending();
        if (search != null && !search.trim().isEmpty()) {
            return vinoRepository.findByNomeContainingIgnoreCase(search, ordinamento);
        }
        return vinoRepository.findAll(ordinamento);
    }

    public Vino salvaVino(Vino vino) {
        // VINCOLO TASK 4: Se il client forza un ID nel JSON, lo azzeriamo 
        // per impedire la corruzione o la sovrascrittura di record esistenti.
        vino.setId(null);
        return vinoRepository.save(vino);
    }

    public Vino dettaglioVino(UUID id) {
        return vinoRepository.findById(id).orElse(null);
    }

    public Vino aggiornaVino(UUID id, Vino datiNuovi) {
        Vino vinoEsistente = vinoRepository.findById(id).orElse(null);
        if (vinoEsistente == null) {
            return null;
        }
        vinoEsistente.setNome(datiNuovi.getNome());
        vinoEsistente.setCantina(datiNuovi.getCantina());
        vinoEsistente.setCategoria(datiNuovi.getCategoria());
        vinoEsistente.setAnno(datiNuovi.getAnno());
        return vinoRepository.save(vinoEsistente);
    }

    public boolean eliminaVino(UUID id) {
        if (!vinoRepository.existsById(id)) {
            return false;
        }
        vinoRepository.deleteById(id);
        return true;
    }

    public void svuotaCatalogo() {
        vinoRepository.deleteAll();
    }
}