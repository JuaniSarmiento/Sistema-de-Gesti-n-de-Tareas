package com.utn.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * Punto de entrada de la API REST de Productos.
 */
@SpringBootApplication
public class ProductosApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProductosApiApplication.class, args);
    }
}
