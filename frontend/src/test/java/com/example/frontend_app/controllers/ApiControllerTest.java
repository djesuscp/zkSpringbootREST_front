package com.example.frontend_app.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.zkoss.zul.Textbox;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiControllerTest {

    private ApiController apiController;

    @Mock
    private Textbox resultBox;

    @Mock
    private HttpResponse<String> mockResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Inyectamos URL de prueba, HttpClient y ObjectMapper
        apiController = spy(new ApiController("http://test", HttpClient.newHttpClient(), new ObjectMapper()));
        apiController.resultBox = resultBox;  // Inyectamos mock del textbox
    }

    @Test
    public void testFetchData_SuccessfulResponse() throws Exception {
        String json = "{\"id\":1,\"userId\":10,\"postId\":100,\"title\":\"Test Title\",\"body\":\"Test Body\"}";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(json);

        doReturn(mockResponse).when(apiController).sendHttpRequest(any(HttpRequest.class));

        apiController.fetchData();

        verify(resultBox).setValue(contains("ID: 1"));
        verify(resultBox).setValue(contains("UserId: 10"));
        verify(resultBox).setValue(contains("PostId: 100"));
        verify(resultBox).setValue(contains("Title: Test Title"));
        verify(resultBox).setValue(contains("Body: Test Body"));
    }

    @Test
    public void testFetchData_Non200Response() throws Exception {
        when(mockResponse.statusCode()).thenReturn(500);
        doReturn(mockResponse).when(apiController).sendHttpRequest(any(HttpRequest.class));

        apiController.fetchData();

        verify(resultBox).setValue("Error: Código de respuesta 500");
    }

    @Test
    public void testFetchData_NullPostOrEmptyFields() throws Exception {
        // JSON que se deserializa a Post con campos nulos
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{}");
        doReturn(mockResponse).when(apiController).sendHttpRequest(any(HttpRequest.class));

        apiController.fetchData();

        verify(resultBox).setValue("Error: No se recibió ningún dato.");
    }

    @Test
    public void testFetchData_Exception() throws Exception {
        doThrow(new RuntimeException("Error de red")).when(apiController).sendHttpRequest(any(HttpRequest.class));

        apiController.fetchData();

        verify(resultBox).setValue(contains("Error: Error de red"));
    }
}