package com.utn.productos.service;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.exception.ProductoNotFoundException;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que contiene la lógica de negocio para la gestión de productos.
 */
@Service
@Transactional
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    
    /**
     * Constructor con inyección de dependencias.
     * @param productoRepository Repositorio de productos
     */
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    /**
     * Crea un nuevo producto.
     * @param productoDTO DTO con los datos del producto a crear
     * @return DTO del producto creado
     */
    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(productoDTO.getCategoria());
        
        Producto productoGuardado = productoRepository.save(producto);
        return convertirAResponse(productoGuardado);
    }
    
    /**
     * Obtiene todos los productos.
     * @return Lista de todos los productos
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto
     * @return DTO del producto encontrado
     * @throws ProductoNotFoundException si no existe el producto
     */
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return convertirAResponse(producto);
    }
    
    /**
     * Obtiene todos los productos de una categoría específica.
     * @param categoria Categoría por la que filtrar
     * @return Lista de productos de la categoría
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar
     * @param productoDTO DTO con los nuevos datos
     * @return DTO del producto actualizado
     * @throws ProductoNotFoundException si no existe el producto
     */
    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(productoDTO.getCategoria());
        
        Producto productoActualizado = productoRepository.save(producto);
        return convertirAResponse(productoActualizado);
    }
    
    /**
     * Actualiza solo el stock de un producto.
     * @param id ID del producto
     * @param stockDTO DTO con el nuevo stock
     * @return DTO del producto actualizado
     * @throws ProductoNotFoundException si no existe el producto
     */
    public ProductoResponseDTO actualizarStock(Long id, ActualizarStockDTO stockDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        
        producto.setStock(stockDTO.getStock());
        
        Producto productoActualizado = productoRepository.save(producto);
        return convertirAResponse(productoActualizado);
    }
    
    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar
     * @throws ProductoNotFoundException si no existe el producto
     */
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }
    
    /**
     * Convierte una entidad Producto a ProductoResponseDTO.
     * @param producto Entidad a convertir
     * @return DTO de respuesta
     */
    private ProductoResponseDTO convertirAResponse(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        );
    }
}
