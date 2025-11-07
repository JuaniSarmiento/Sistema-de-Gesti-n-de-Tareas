package com.utn.tareas.service;

/**
 * Interfaz que define el contrato para los servicios de mensajería.
 * Las implementaciones varían según el perfil activo (dev/prod).
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
public interface MensajeService {
    
    /**
     * Genera un mensaje de bienvenida personalizado según el perfil
     * 
     * @param appName Nombre de la aplicación
     * @return Mensaje de bienvenida formateado
     */
    String bienvenida(String appName);
    
    /**
     * Genera un mensaje de despedida personalizado según el perfil
     * 
     * @param appName Nombre de la aplicación
     * @return Mensaje de despedida formateado
     */
    String despedida(String appName);
}
