package com.utn.productos.repository;

import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Producto.
 * Proporciona operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Busca todos los productos de una categoría específica.
     * @param categoria La categoría por la cual filtrar
     * @return Lista de productos de la categoría especificada
     */
    List<Producto> findByCategoria(Categoria categoria);
}
