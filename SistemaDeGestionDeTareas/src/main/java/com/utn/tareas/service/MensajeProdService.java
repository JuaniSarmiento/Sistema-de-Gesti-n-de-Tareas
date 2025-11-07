package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de mensajería para el perfil de PRODUCCIÓN.
 * Proporciona mensajes concisos y profesionales optimizados para entornos productivos.
 * 
 * Esta clase solo estará activa cuando el perfil "prod" esté configurado.
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
@Service
@Profile("prod")
public class MensajeProdService implements MensajeService {
    
    @Override
    public String bienvenida(String appName) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("═══════════════════════════════════════════\n");
        sb.append("  " + appName + " v1.0\n");
        sb.append("  Modo: PRODUCCIÓN\n");
        sb.append("  Sistema iniciado correctamente\n");
        sb.append("═══════════════════════════════════════════\n");
        return sb.toString();
    }
    
    @Override
    public String despedida(String appName) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("═══════════════════════════════════════════\n");
        sb.append("  " + appName + "\n");
        sb.append("  Sesión finalizada\n");
        sb.append("  Estado: OK\n");
        sb.append("═══════════════════════════════════════════\n");
        return sb.toString();
    }
}
