package com.utn.productos.exception;

/**
 * Excepción personalizada para cuando no se encuentra un producto.
 */
public class ProductoNotFoundException extends RuntimeException {
    
    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public ProductoNotFoundException(Long id) {
        super("Producto no encontrado con ID: " + id);
    }
}
