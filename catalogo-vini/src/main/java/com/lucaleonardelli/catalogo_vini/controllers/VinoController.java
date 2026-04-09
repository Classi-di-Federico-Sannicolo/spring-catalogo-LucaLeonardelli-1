package com.lucaleonardelli.catalogo_vini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;

import java.util.List;

@Controller
public class VinoController {

    @Autowired
    private VinoRepository vinoRepository;

    ////////////////// HOMEPAGE
    @GetMapping("/")
    public String home(Model model) {
        List<Vino> vini = vinoRepository.findAll();
        model.addAttribute("listaVini", vini);
        return "index";
    }

    ////////////////// FORM CREAZIONE
    @GetMapping("/new")
    public String mostraFormAggiunta(Model model) {
        model.addAttribute("vino", new Vino());
        return "form";
    }
    @PostMapping("/new")
    public String salvaVino(Vino vino) {
        vinoRepository.save(vino);
        return "redirect:/";
    }

    ////////////////// PAGINA ELIMINAZIONE
    @GetMapping("/clear")
    public String svuotaCatalogo() {
        vinoRepository.deleteAll();
        return "redirect:/";
    }
}