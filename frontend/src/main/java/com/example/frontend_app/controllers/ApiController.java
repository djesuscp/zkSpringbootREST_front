package com.example.frontend_app.controllers;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.frontend_app.entities.Post;

public class ApiController extends SelectorComposer<Window> {

    @Wire private Button fetchButton;
    @Wire Textbox resultBox;

    private final String backendUrl; 
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // Constructor por defecto (producción)
    public ApiController() {
        String envUrl = System.getenv("BACKEND_API_URL");
        String finalUrl;
        if(envUrl == null || envUrl.isBlank()) {
            finalUrl = "http://backend_app:8080/api/external-post";
        }
        else {
            finalUrl = envUrl;
        }
        this.backendUrl = finalUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Constructor para inyección (tests)
    public ApiController(String backendUrl, HttpClient httpClient, ObjectMapper objectMapper) {
        this.backendUrl = backendUrl;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Listen("onClick = #fetchButton")
    public void fetchData() {
        try {
            System.out.println("----BACKEND_URI----: " + backendUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(backendUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = sendHttpRequest(request);

            if (response.statusCode() != 200) {
                resultBox.setValue("Error: Código de respuesta " + response.statusCode());
                return;
            }

            Post post = objectMapper.readValue(response.body(), Post.class);

            // Validar que post tiene datos importantes
            if (post == null || post.getId() == null || post.getUserId() == null || post.getPostId() == null) {
                resultBox.setValue("Error: No se recibió ningún dato.");
                return;
            }

            resultBox.setValue("Datos recibidos:\n" +
                    "ID: " + post.getId() + "\n" +
                    "UserId: " + post.getUserId() + "\n" +
                    "PostId: " + post.getPostId() + "\n" +
                    "Title: " + post.getTitle() + "\n" +
                    "Body: " + post.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            resultBox.setValue("Error: " + e.getMessage());
        }
    }

    /**
     * Método protegido que realiza la petición HTTP.
     * Permite ser sobrescrito o mockeado en tests.
     */
    protected HttpResponse<String> sendHttpRequest(HttpRequest request) throws Exception {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}