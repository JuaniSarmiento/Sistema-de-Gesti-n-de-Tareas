package com.utn.tareas.repository;

import com.utn.tareas.model.Tarea;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define el contrato para el repositorio de tareas.
 * Proporciona operaciones básicas de persistencia (CRUD).
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
public interface TareaRepository {
    
    /**
     * Obtiene todas las tareas almacenadas
     * 
     * @return Lista con todas las tareas
     */
    List<Tarea> listarTodas();
    
    /**
     * Guarda una nueva tarea en el repositorio
     * 
     * @param tarea La tarea a guardar
     */
    void guardar(Tarea tarea);
    
    /**
     * Busca una tarea por su identificador único
     * 
     * @param id El identificador de la tarea
     * @return Optional que contiene la tarea si existe, vacío en caso contrario
     */
    Optional<Tarea> buscarPorId(Long id);
    
    /**
     * Elimina una tarea del repositorio
     * 
     * @param id El identificador de la tarea a eliminar
     */
    void eliminar(Long id);
}
