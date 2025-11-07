package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de mensajería para el perfil de DESARROLLO.
 * Proporciona mensajes detallados y amistosos para facilitar el desarrollo.
 * 
 * Esta clase solo estará activa cuando el perfil "dev" esté configurado.
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
@Service
@Profile("dev")
public class MensajeDevService implements MensajeService {
    
    @Override
    public String bienvenida(String appName) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("╔════════════════════════════════════════════════════════════╗\n");
        sb.append("║                                                            ║\n");
        sb.append("║         🚀 BIENVENIDO AL MODO DESARROLLO 🚀                ║\n");
        sb.append("║                                                            ║\n");
        sb.append(String.format("║  %-56s  ║\n", "Aplicación: " + appName));
        sb.append("║                                                            ║\n");
        sb.append("║  → Perfil activo: DEV                                      ║\n");
        sb.append("║  → Logging detallado activado                              ║\n");
        sb.append("║  → Estadísticas habilitadas                                ║\n");
        sb.append("║  → Límite de tareas: 10                                    ║\n");
        sb.append("║                                                            ║\n");
        sb.append("║  ¡Listo para desarrollar y probar funcionalidades! 💻      ║\n");
        sb.append("║                                                            ║\n");
        sb.append("╚════════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
    
    @Override
    public String despedida(String appName) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("╔════════════════════════════════════════════════════════════╗\n");
        sb.append("║                                                            ║\n");
        sb.append("║         👋 HASTA PRONTO DESARROLLADOR 👋                   ║\n");
        sb.append("║                                                            ║\n");
        sb.append(String.format("║  %-56s  ║\n", appName + " - Sesión finalizada"));
        sb.append("║                                                            ║\n");
        sb.append("║  ✓ Todas las operaciones completadas                      ║\n");
        sb.append("║  ✓ Cambios guardados en memoria                           ║\n");
        sb.append("║                                                            ║\n");
        sb.append("║  💡 Tip: Revisa los logs para más detalles                ║\n");
        sb.append("║                                                            ║\n");
        sb.append("║  ¡Feliz codificación! 🎉                                  ║\n");
        sb.append("║                                                            ║\n");
        sb.append("╚════════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
