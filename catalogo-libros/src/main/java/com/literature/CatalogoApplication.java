package com.literature; // Asegúrate de que coincida con el paquete de tus otras clases

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Aquí llamamos a la lógica que escribimos antes
        Principal principal = new Principal();
        principal.muestraMenu();
    }
}
