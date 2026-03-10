package com.tu_paquete.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// Ignoramos los datos del JSON que no nos interesan
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DatosAutor> autor,
    @JsonAlias("languages") List<String> idiomas,
    @JsonAlias("download_count") Double numeroDeDescargas
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
    @JsonAlias("name") String nombre,
    @JsonAlias("birth_year") String fechaDeNacimiento,
    @JsonAlias("death_year") String fechaDeFallecimiento
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
public record Resumen(
    @JsonAlias("results") List<DatosLibro> resultados
) {}
