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
                
                Vino vino1 = new Vino();
                vino1.setNome("Barolo Riserva Monfortino");
                vino1.setCantina("Giacomo Conterno");
                vino1.setCategoria("Rosso");
                vino1.setAnno(2015);

                Vino vino2 = new Vino();
                vino2.setNome("Giulio Ferrari Riserva del Fondatore");
                vino2.setCantina("Cantine Ferrari");
                vino2.setCategoria("Bollicine");
                vino2.setAnno(2008);

                Vino vino3 = new Vino();
                vino3.setNome("Cervaro della Sala");
                vino3.setCantina("Antinori");
                vino3.setCategoria("Bianco");
                vino3.setAnno(2020);

                Vino vino4 = new Vino();
                vino4.setNome("Five Roses Anniversario");
                vino4.setCantina("Leone de Castris");
                vino4.setCategoria("Rosato");
                vino4.setAnno(2022);

                Vino vino5 = new Vino();
                vino5.setNome("Sassicaia");
                vino5.setCantina("Tenuta San Guido");
                vino5.setCategoria("Rosso");
                vino5.setAnno(2018);

                Vino vino6 = new Vino();
                vino6.setNome("Franciacorta Cuvée Prestige");
                vino6.setCantina("Ca' del Bosco");
                vino6.setCategoria("Bollicine");
                vino6.setAnno(2019);

                Vino vino7 = new Vino();
                vino7.setNome("Gewürztraminer Nussbaumer");
                vino7.setCantina("Cantina Tramin");
                vino7.setCategoria("Bianco");
                vino7.setAnno(2021);

                Vino vino8 = new Vino();
                vino8.setNome("Amarone della Valpolicella Classico");
                vino8.setCantina("Giuseppe Quintarelli");
                vino8.setCategoria("Rosso");
                vino8.setAnno(2013);

                Vino vino9 = new Vino();
                vino9.setNome("Vintage Tunina");
                vino9.setCantina("Jermann");
                vino9.setCategoria("Bianco");
                vino9.setAnno(2020);

                Vino vino10 = new Vino();
                vino10.setNome("Dom Pérignon Vintage");
                vino10.setCantina("Moët & Chandon");
                vino10.setCategoria("Bollicine");
                vino10.setAnno(2012);

                vinoRepository.saveAll(List.of(
                    vino1, vino2, vino3, vino4, vino5, 
                    vino6, vino7, vino8, vino9, vino10
                ));
                
                System.out.println("Cantina inizializzata con le bottiglie di default!");
            }
        };
    }
}