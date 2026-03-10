package com.literature; 

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obtenerDatos(String url) {
        // Creamos el cliente que hará la petición
        HttpClient client = HttpClient.newHttpClient();
        
        // Configuramos la dirección (URL) a la que vamos a ir
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        HttpResponse<String> response = null;
        
        try {
            // Intentamos obtener la respuesta
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            // Si algo sale mal (sin internet, etc), lanzamos un error
            throw new RuntimeException("Error al conectar con la API: " + e.getMessage());
        }

        // Devolvemos el cuerpo de la respuesta (el JSON)
        return response.body();
    }
}
