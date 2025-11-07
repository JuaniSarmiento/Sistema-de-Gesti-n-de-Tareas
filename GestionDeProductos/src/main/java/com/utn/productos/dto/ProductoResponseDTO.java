package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de productos.
 * Representa la información que se devuelve al cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Categoria categoria;
}
