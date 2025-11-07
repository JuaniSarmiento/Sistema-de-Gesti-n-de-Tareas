package com.utn.tareas.repository;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación en memoria del repositorio de tareas.
 * Utiliza una lista para almacenar las tareas y un generador atómico de IDs.
 * Se inicializa con datos de ejemplo para facilitar las pruebas.
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
@Repository
public class TareaRepositoryImpl implements TareaRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(TareaRepositoryImpl.class);
    
    /**
     * Lista que almacena las tareas en memoria
     */
    private final List<Tarea> tareas;
    
    /**
     * Generador atómico de IDs únicos para las tareas
     */
    private final AtomicLong generadorId;
    
    /**
     * Constructor que inicializa el repositorio con datos de ejemplo
     */
    public TareaRepositoryImpl() {
        this.tareas = new ArrayList<>();
        this.generadorId = new AtomicLong(0);
        inicializarDatosEjemplo();
        logger.debug("Repositorio de tareas inicializado con {} tareas de ejemplo", tareas.size());
    }
    
    /**
     * Inicializa el repositorio con tareas de ejemplo para demostración
     */
    private void inicializarDatosEjemplo() {
        guardar(new Tarea(null, "Implementar módulo de autenticación", false, Prioridad.ALTA));
        guardar(new Tarea(null, "Revisar documentación de Spring Boot", false, Prioridad.MEDIA));
        guardar(new Tarea(null, "Actualizar dependencias del proyecto", false, Prioridad.BAJA));
        guardar(new Tarea(null, "Realizar pruebas unitarias", false, Prioridad.ALTA));
        guardar(new Tarea(null, "Optimizar consultas a base de datos", false, Prioridad.MEDIA));
    }
    
    @Override
    public List<Tarea> listarTodas() {
        logger.debug("Listando todas las tareas. Total: {}", tareas.size());
        return new ArrayList<>(tareas); // Retorna una copia para evitar modificaciones externas
    }
    
    @Override
    public void guardar(Tarea tarea) {
        if (tarea.getId() == null) {
            // Asignar nuevo ID si es una tarea nueva
            tarea.setId(generadorId.incrementAndGet());
            logger.debug("Guardando nueva tarea con ID: {}", tarea.getId());
        } else {
            logger.debug("Actualizando tarea con ID: {}", tarea.getId());
        }
        
        // Si la tarea ya existe (mismo ID), la reemplazamos
        tareas.removeIf(t -> t.getId().equals(tarea.getId()));
        tareas.add(tarea);
        
        logger.info("Tarea guardada exitosamente: {}", tarea.getDescripcion());
    }
    
    @Override
    public Optional<Tarea> buscarPorId(Long id) {
        logger.debug("Buscando tarea con ID: {}", id);
        Optional<Tarea> resultado = tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
        
        if (resultado.isPresent()) {
            logger.debug("Tarea encontrada: {}", resultado.get().getDescripcion());
        } else {
            logger.warn("No se encontró tarea con ID: {}", id);
        }
        
        return resultado;
    }
    
    @Override
    public void eliminar(Long id) {
        logger.debug("Intentando eliminar tarea con ID: {}", id);
        boolean eliminada = tareas.removeIf(t -> t.getId().equals(id));
        
        if (eliminada) {
            logger.info("Tarea con ID {} eliminada exitosamente", id);
        } else {
            logger.warn("No se pudo eliminar la tarea con ID: {} (no existe)", id);
        }
    }
}
