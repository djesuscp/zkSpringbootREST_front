package com.example.frontend_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Fuerza a SpringBoot a escanear el paquete correcto de la aplicación.
@SpringBootApplication(scanBasePackages = "com.example.frontend_app")
public class ProjectExercise1Application {
    public static void main(String[] args) {
        SpringApplication.run(ProjectExercise1Application.class, args);
    }
}