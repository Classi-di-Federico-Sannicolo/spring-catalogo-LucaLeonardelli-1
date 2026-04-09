package com.lucaleonardelli.catalogo_vini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;

import java.util.List;

@Controller
public class VinoController {

    @Autowired
    private VinoRepository vinoRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Vino> vini = vinoRepository.findAll();
        model.addAttribute("listaVini", vini);
        return "index";
    }
}