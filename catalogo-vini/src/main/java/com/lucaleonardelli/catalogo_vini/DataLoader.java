package com.lucaleonardelli.catalogo_vini;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lucaleonardelli.catalogo_vini.domain.Vino;
import com.lucaleonardelli.catalogo_vini.repositories.VinoRepository;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner inizializzaDatabase(VinoRepository vinoRepository) {
        return args -> {
            if (vinoRepository.count() == 0) {
                
                // --- ROSSI ---
                Vino vino1 = creaVino("Barolo Riserva Monfortino", "Giacomo Conterno", "Rosso", 2015);
                Vino vino2 = creaVino("Sassicaia", "Tenuta San Guido", "Rosso", 2018);
                Vino vino3 = creaVino("Amarone Classico", "Giuseppe Quintarelli", "Rosso", 2013);
                Vino vino4 = creaVino("Brunello di Montalcino Biondi Santi", "Tenuta Greppo", "Rosso", 2012);

                // --- BIANCHI ---
                Vino vino5 = creaVino("Cervaro della Sala", "Antinori", "Bianco", 2020);
                Vino vino6 = creaVino("Vintage Tunina", "Jermann", "Bianco", 2020);

                // --- BOLLICINE ---
                Vino vino7 = creaVino("Giulio Ferrari Riserva del Fondatore", "Cantine Ferrari", "Bollicine", 2008);
                Vino vino8 = creaVino("Dom Pérignon Plénitude 2", "Moët & Chandon", "Bollicine", 2003);

                // --- ROSATI ---
                Vino vino9 = creaVino("Five Roses Anniversario", "Leone de Castris", "Rosato", 2022);

                // --- DOLCI (Novità!) ---
                Vino vino10 = creaVino("Château d'Yquem", "LVMH", "Dolce", 2017); // Il più famoso Sauternes al mondo
                Vino vino11 = creaVino("Passito di Pantelleria Ben Ryé", "Donnafugata", "Dolce", 2021);
                Vino vino12 = creaVino("Tokaji Aszú 6 Puttonyos", "Royal Tokaji", "Dolce", 2016); // "Il vino dei Re" ungherese

                // --- FORTIFICATI (Novità!) ---
                Vino vino13 = creaVino("Marsala Superiore Riserva", "Marco De Bartoli", "Fortificato", 1987);
                Vino vino14 = creaVino("Porto Vintage", "Graham's", "Fortificato", 2000);

                vinoRepository.saveAll(List.of(
                    vino1, vino2, vino3, vino4, vino5, vino6, vino7, 
                    vino8, vino9, vino10, vino11, vino12, vino13, vino14
                ));
                
                System.out.println("Eremo D'Ambra: Cantina popolata con 14 eccellenze mondiali!");
            }
        };
    }

    // Metodo helper per velocizzare la creazione degli oggetti senza ripetere tutto il codice
    private Vino creaVino(String nome, String cantina, String categoria, int anno) {
        Vino v = new Vino();
        v.setNome(nome);
        v.setCantina(cantina);
        v.setCategoria(categoria);
        v.setAnno(anno);
        return v;
    }
}