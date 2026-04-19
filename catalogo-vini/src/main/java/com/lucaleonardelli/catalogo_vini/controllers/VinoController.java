package com.lucaleonardelli.catalogo_vini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;


import java.util.UUID;
import java.util.Optional;
import java.util.List;

@Controller
public class VinoController {

    @Autowired
    private VinoRepository vinoRepository;

    ////////////////// HOMEPAGE //////////////////
    @GetMapping("/")
    public String home( Model model, 
                        @RequestParam(name = "ricerca", required = false) String search,
                        @RequestParam(name = "sort", required = false, defaultValue = "nome") String sortField) {

        List<Vino> listaVini;
        
        Sort ordinamento = Sort.by(sortField).ascending();

        // Ricerca x nome
        if (search != null && !search.trim().isEmpty()) {
            listaVini = vinoRepository.findByNomeContainingIgnoreCase(search, ordinamento);
        } else {
            listaVini = vinoRepository.findAll(ordinamento);
        }
        model.addAttribute("listaVini", listaVini);
        model.addAttribute("valoreRicerca", search); 
        
        return "index";
    }

    ////////////////// FORM CREAZIONE //////////////////
    @GetMapping("/new")
    public String mostraFormAggiunta(Model model) {
        model.addAttribute("vino", new Vino());

        return "form";
    }
    @PostMapping("/new")
    public String salvaVino(Vino vino, RedirectAttributes redirectAttributes) {
        Vino vinoSalvato = vinoRepository.save(vino);
        redirectAttributes.addFlashAttribute("messaggioSuccesso", "Vino aggiunto correttamente al catalogo!");

        return "redirect:/item/" + vinoSalvato.getId();
    }

    ////////////////// PAGINA DETTAGLIO //////////////////
    @GetMapping("/item/{id}")
    public String dettaglioVino(@PathVariable("id") UUID id, Model model) {
        Optional<Vino> vinoTrovato = vinoRepository.findById(id);
        
        if (vinoTrovato.isPresent()) {
            model.addAttribute("vino", vinoTrovato.get());
            return "dettaglio";
        } else {
            return "redirect:/"; 
        }
    }

    ////////////////// PAGINA ELIMINAZIONE //////////////////
    @GetMapping("/clear")
    public String svuotaCatalogo() {
        vinoRepository.deleteAll();

        return "redirect:/";
    }

    ////////////////// PATH PER ELIMINAZIONE ENTRY //////////////////
    @GetMapping("/delete/{id}")
    public String eliminaVino(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        vinoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("messaggioEliminazione", "Vino eliminato con successo dal catalogo!");
        
        return "redirect:/";
    }

    ////////////////// FORM MODIFICA //////////////////
    @GetMapping("/modifica/{id}")
    public String mostraFormModifica(@PathVariable("id") UUID id, Model model) {
        Optional<Vino> vinoTrovato = vinoRepository.findById(id);
        
        if (vinoTrovato.isPresent()) {
            model.addAttribute("vino", vinoTrovato.get());
            return "modifica";
        } else {
            return "redirect:/"; 
        }
    }

    ////////////////// AGGIORNAMENTO ENTRY //////////////////
    @PostMapping("/aggiorna")
    public String aggiornaVino(@ModelAttribute Vino vino, RedirectAttributes redirectAttributes) {
        vinoRepository.save(vino);
        
        redirectAttributes.addFlashAttribute("messaggioSuccesso", "Modifiche salvate con successo!");
        
        return "redirect:/";
    }
}