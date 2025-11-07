package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 * Expone endpoints para operaciones CRUD sobre productos.
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API REST para gestión de productos")
public class ProductoController {
    
    private final ProductoService productoService;
    
    /**
     * Constructor con inyección de dependencias.
     * @param productoService Servicio de productos
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    /**
     * Lista todos los productos disponibles.
     * @return Lista de productos
     */
    @GetMapping
    @Operation(summary = "Listar todos los productos", 
               description = "Obtiene la lista completa de productos disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }
    
    /**
     * Obtiene un producto específico por su ID.
     * @param id ID del producto
     * @return Producto encontrado
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", 
               description = "Busca y retorna un producto específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        ProductoResponseDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }
    
    /**
     * Filtra productos por categoría.
     * @param categoria Categoría a filtrar
     * @return Lista de productos de la categoría especificada
     */
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar productos por categoría", 
               description = "Obtiene todos los productos que pertenecen a una categoría específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos filtrados exitosamente")
    })
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Categoria categoria) {
        List<ProductoResponseDTO> productos = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }
    
    /**
     * Crea un nuevo producto.
     * @param dto DTO con los datos del producto a crear
     * @return Producto creado
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo producto", 
               description = "Registra un nuevo producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoResponseDTO productoCreado = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }
    
    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar
     * @param dto DTO con los nuevos datos del producto
     * @return Producto actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto existente", 
               description = "Modifica todos los datos de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody ProductoDTO dto) {
        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(productoActualizado);
    }
    
    /**
     * Actualiza únicamente el stock de un producto.
     * @param id ID del producto
     * @param dto DTO con el nuevo stock
     * @return Producto con stock actualizado
     */
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Actualizar solo el stock del producto", 
               description = "Modifica únicamente la cantidad en stock de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Valor de stock inválido")
    })
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @PathVariable Long id, 
            @Valid @RequestBody ActualizarStockDTO dto) {
        ProductoResponseDTO productoActualizado = productoService.actualizarStock(id, dto);
        return ResponseEntity.ok(productoActualizado);
    }
    
    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto por ID", 
               description = "Elimina permanentemente un producto del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
