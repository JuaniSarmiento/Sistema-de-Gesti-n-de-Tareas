package com.utn.tareas.service;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.repository.TareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio relacionada con las tareas.
 * Implementa validaciones, filtros y operaciones sobre las tareas.
 * 
 * Este servicio se configura mediante propiedades externas y utiliza
 * inyección de dependencias por constructor.
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
@Service
public class TareaService {
    
    private static final Logger logger = LoggerFactory.getLogger(TareaService.class);
    
    /**
     * Repositorio de tareas inyectado
     */
    private final TareaRepository tareaRepository;
    
    /**
     * Número máximo de tareas permitidas (configurado por perfil)
     */
    @Value("${app.max-tareas}")
    private int maxTareas;
    
    /**
     * Nombre de la aplicación (configurado externamente)
     */
    @Value("${app.nombre}")
    private String nombreApp;
    
    /**
     * Flag que indica si se deben mostrar estadísticas (configurado por perfil)
     */
    @Value("${app.mostrar-estadisticas}")
    private boolean mostrarEstadisticas;
    
    /**
     * Constructor con inyección de dependencias
     * 
     * @param tareaRepository Repositorio de tareas
     */
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
        logger.info("TareaService inicializado correctamente");
    }
    
    /**
     * Agrega una nueva tarea al sistema
     * 
     * @param descripcion Descripción de la tarea
     * @param prioridad Nivel de prioridad de la tarea
     * @throws IllegalStateException Si se excede el límite de tareas
     * @throws IllegalArgumentException Si la descripción está vacía
     */
    public void agregarTarea(String descripcion, Prioridad prioridad) {
        // Validar descripción
        if (descripcion == null || descripcion.trim().isEmpty()) {
            logger.error("Intento de agregar tarea con descripción vacía");
            throw new IllegalArgumentException("La descripción de la tarea no puede estar vacía");
        }
        
        // Validar límite de tareas
        List<Tarea> tareasActuales = tareaRepository.listarTodas();
        if (tareasActuales.size() >= maxTareas) {
            logger.error("Límite de tareas alcanzado: {}/{}", tareasActuales.size(), maxTareas);
            throw new IllegalStateException(
                String.format("Se ha alcanzado el límite máximo de %d tareas. " +
                             "No se pueden agregar más tareas.", maxTareas)
            );
        }
        
        // Crear y guardar la tarea
        Tarea nuevaTarea = new Tarea(null, descripcion.trim(), false, prioridad);
        tareaRepository.guardar(nuevaTarea);
        
        logger.info("Nueva tarea agregada: '{}' con prioridad {}", descripcion, prioridad);
        System.out.println("✓ Tarea agregada exitosamente: " + nuevaTarea);
    }
    
    /**
     * Lista todas las tareas del sistema
     * 
     * @return Lista de todas las tareas
     */
    public List<Tarea> listarTodas() {
        List<Tarea> tareas = tareaRepository.listarTodas();
        logger.debug("Listando todas las tareas. Total: {}", tareas.size());
        return tareas;
    }
    
    /**
     * Lista únicamente las tareas pendientes (no completadas)
     * 
     * @return Lista de tareas pendientes
     */
    public List<Tarea> listarPendientes() {
        List<Tarea> pendientes = tareaRepository.listarTodas().stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
        
        logger.debug("Tareas pendientes encontradas: {}", pendientes.size());
        return pendientes;
    }
    
    /**
     * Lista únicamente las tareas completadas
     * 
     * @return Lista de tareas completadas
     */
    public List<Tarea> listarCompletadas() {
        List<Tarea> completadas = tareaRepository.listarTodas().stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
        
        logger.debug("Tareas completadas encontradas: {}", completadas.size());
        return completadas;
    }
    
    /**
     * Marca una tarea como completada
     * 
     * @param id Identificador de la tarea
     * @throws IllegalArgumentException Si la tarea no existe
     */
    public void marcarComoCompletada(Long id) {
        Optional<Tarea> tareaOpt = tareaRepository.buscarPorId(id);
        
        if (tareaOpt.isEmpty()) {
            logger.error("Intento de completar tarea inexistente con ID: {}", id);
            throw new IllegalArgumentException(
                String.format("No existe una tarea con el ID %d", id)
            );
        }
        
        Tarea tarea = tareaOpt.get();
        
        if (tarea.isCompletada()) {
            logger.warn("La tarea con ID {} ya estaba completada", id);
            System.out.println("⚠ La tarea ya estaba marcada como completada");
            return;
        }
        
        tarea.setCompletada(true);
        tareaRepository.guardar(tarea);
        
        logger.info("Tarea con ID {} marcada como completada: '{}'", id, tarea.getDescripcion());
        System.out.println("✓ Tarea completada: " + tarea.getDescripcion());
    }
    
    /**
     * Obtiene estadísticas detalladas sobre las tareas
     * 
     * @return Mapa con las estadísticas calculadas
     */
    public Map<String, Object> obtenerEstadisticas() {
        List<Tarea> todasLasTareas = tareaRepository.listarTodas();
        
        // Calcular estadísticas
        long total = todasLasTareas.size();
        long completadas = todasLasTareas.stream().filter(Tarea::isCompletada).count();
        long pendientes = total - completadas;
        
        long altaPrioridad = todasLasTareas.stream()
                .filter(t -> t.getPrioridad() == Prioridad.ALTA)
                .count();
        long mediaPrioridad = todasLasTareas.stream()
                .filter(t -> t.getPrioridad() == Prioridad.MEDIA)
                .count();
        long bajaPrioridad = todasLasTareas.stream()
                .filter(t -> t.getPrioridad() == Prioridad.BAJA)
                .count();
        
        double porcentajeCompletadas = total > 0 ? (completadas * 100.0 / total) : 0.0;
        
        // Crear mapa de estadísticas
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("total", total);
        estadisticas.put("completadas", completadas);
        estadisticas.put("pendientes", pendientes);
        estadisticas.put("porcentajeCompletadas", porcentajeCompletadas);
        estadisticas.put("altaPrioridad", altaPrioridad);
        estadisticas.put("mediaPrioridad", mediaPrioridad);
        estadisticas.put("bajaPrioridad", bajaPrioridad);
        estadisticas.put("limiteMaximo", maxTareas);
        estadisticas.put("espacioDisponible", maxTareas - total);
        
        logger.debug("Estadísticas calculadas: {} total, {} completadas, {} pendientes", 
                    total, completadas, pendientes);
        
        return estadisticas;
    }
    
    /**
     * Muestra las estadísticas en consola si está habilitado en la configuración
     */
    public void mostrarEstadisticasEnConsola() {
        if (!mostrarEstadisticas) {
            logger.debug("Estadísticas deshabilitadas en este perfil");
            return;
        }
        
        Map<String, Object> stats = obtenerEstadisticas();
        
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│           📊 ESTADÍSTICAS DEL SISTEMA              │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.println(String.format("│  Total de tareas:           %3d / %-3d             │", 
            stats.get("total"), stats.get("limiteMaximo")));
        System.out.println(String.format("│  Tareas completadas:        %-3d                    │", 
            stats.get("completadas")));
        System.out.println(String.format("│  Tareas pendientes:         %-3d                    │", 
            stats.get("pendientes")));
        System.out.println(String.format("│  Progreso:                  %.1f%%                  │", 
            stats.get("porcentajeCompletadas")));
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.println("│           📋 DISTRIBUCIÓN POR PRIORIDAD            │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.println(String.format("│  🔴 Alta:                   %-3d                    │", 
            stats.get("altaPrioridad")));
        System.out.println(String.format("│  🟡 Media:                  %-3d                    │", 
            stats.get("mediaPrioridad")));
        System.out.println(String.format("│  🟢 Baja:                   %-3d                    │", 
            stats.get("bajaPrioridad")));
        System.out.println("└─────────────────────────────────────────────────────┘");
        
        logger.info("Estadísticas mostradas en consola");
    }
    
    /**
     * Obtiene el nombre de la aplicación configurado
     * 
     * @return Nombre de la aplicación
     */
    public String getNombreApp() {
        return nombreApp;
    }
    
    /**
     * Obtiene el límite máximo de tareas configurado
     * 
     * @return Límite máximo de tareas
     */
    public int getMaxTareas() {
        return maxTareas;
    }
}
