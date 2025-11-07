package com.utn.tareas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una tarea en el sistema de gestión.
 * Utiliza Lombok para generar automáticamente getters, setters, 
 * toString, equals y hashCode.
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {
    
    /**
     * Identificador único de la tarea
     */
    private Long id;
    
    /**
     * Descripción detallada de la tarea
     */
    private String descripcion;
    
    /**
     * Indica si la tarea ha sido completada
     */
    private boolean completada;
    
    /**
     * Nivel de prioridad de la tarea
     */
    private Prioridad prioridad;
    
    /**
     * Devuelve una representación legible del estado de la tarea
     * 
     * @return String con el estado formateado
     */
    public String estadoFormateado() {
        return completada ? "✓ COMPLETADA" : "○ PENDIENTE";
    }
    
    /**
     * Devuelve una representación completa de la tarea
     * 
     * @return String con todos los datos de la tarea formateados
     */
    @Override
    public String toString() {
        return String.format("[ID: %d] %s | %s | Prioridad: %s", 
            id, 
            estadoFormateado(), 
            descripcion, 
            prioridad);
    }
}
