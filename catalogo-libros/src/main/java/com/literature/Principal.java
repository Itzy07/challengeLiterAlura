package com.tu_paquete;

import com.tu_paquete.modelos.DatosLibro;
import com.tu_paquete.modelos.Resumen;
import com.tu_paquete.servicio.ConsumoAPI;
import com.tu_paquete.servicio.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    // 1. ATRIBUTOS (Van al principio de la clase)
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com";
    private List<DatosLibro> librosBuscados = new ArrayList<>();

    // 2. MÉTODO PRINCIPAL DEL MENÚ
    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("1- Buscar libro por título, 4- Autores vivos, 5- Idioma, 0- Salir");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Saliendo...");
            }
        }
    }

    // 3. MÉTODOS DE LÓGICA (Tus funciones)
    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro:");
        var nombreLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Resumen.class);

        Optional<DatosLibro> libroBuscado = datos.resultados().stream().findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibro libro = libroBuscado.get();
            librosBuscados.add(libro); // IMPORTANTE: Agregarlo a la lista para que las opciones 4 y 5 funcionen
            System.out.println("Libro Encontrado: " + libro.titulo());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año que desea consultar:");
        var anio = teclado.nextInt();
        teclado.nextLine();
        
        librosBuscados.stream()
            .flatMap(l -> l.autor().stream())
            .filter(a -> {
                int nacimiento = Integer.parseInt(a.fechaDeNacimiento());
                int fallecimiento = (a.fechaDeFallecimiento() == null) ? 
                                     Integer.MAX_VALUE : Integer.parseInt(a.fechaDeFallecimiento());
                return nacimiento <= anio && fallecimiento >= anio;
            })
            .distinct()
            .forEach(a -> System.out.println("Autor: " + a.nombre()));
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (es, en, fr):");
        var idioma = teclado.nextLine().toLowerCase();

        List<DatosLibro> librosFiltrados = librosBuscados.stream()
                .filter(l -> l.idiomas().contains(idioma))
                .collect(Collectors.toList());

        librosFiltrados.forEach(l -> System.out.println("Libro: " + l.titulo()));
    }
}
