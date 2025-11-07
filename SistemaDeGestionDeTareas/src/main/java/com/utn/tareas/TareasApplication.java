package com.utn.tareas;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.service.MensajeService;
import com.utn.tareas.service.TareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Clase principal de la aplicación Sistema de Gestión de Tareas.
 * 
 * Esta aplicación demuestra el uso de conceptos fundamentales de Spring Boot:
 * - Inyección de Dependencias por constructor
 * - Perfiles de configuración (dev/prod)
 * - Configuración externa mediante properties
 * - Arquitectura en capas desacoplada
 * 
 * La aplicación se ejecuta en modo consola mediante CommandLineRunner.
 * 
 * @author Sistema de Gestión de Tareas UTN
 * @version 1.0
 */
@SpringBootApplication
public class TareasApplication implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(TareasApplication.class);
    
    /**
     * Servicio de gestión de tareas (inyectado por constructor)
     */
    private final TareaService tareaService;
    
    /**
     * Servicio de mensajería (implementación varía según perfil activo)
     */
    private final MensajeService mensajeService;
    
    /**
     * Constructor con inyección de dependencias.
     * Spring automáticamente inyecta las implementaciones correctas
     * según el perfil activo.
     * 
     * @param tareaService Servicio de gestión de tareas
     * @param mensajeService Servicio de mensajería (dev o prod)
     */
    public TareasApplication(TareaService tareaService, MensajeService mensajeService) {
        this.tareaService = tareaService;
        this.mensajeService = mensajeService;
        logger.info("TareasApplication inicializada con inyección de dependencias");
    }
    
    /**
     * Punto de entrada de la aplicación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(TareasApplication.class, args);
    }
    
    /**
     * Método ejecutado automáticamente después de que Spring Boot inicie el contexto.
     * Implementa el flujo completo de demostración de la aplicación.
     * 
     * @param args Argumentos de línea de comandos
     */
    @Override
    public void run(String... args) {
        try {
            // ═══════════════════════════════════════════════════════════
            // 1️⃣ MENSAJE DE BIENVENIDA
            // ═══════════════════════════════════════════════════════════
            System.out.println(mensajeService.bienvenida(tareaService.getNombreApp()));
            logger.info("Iniciando flujo de demostración de la aplicación");
            
            // ═══════════════════════════════════════════════════════════
            // 2️⃣ LISTAR TAREAS INICIALES
            // ═══════════════════════════════════════════════════════════
            System.out.println("\n📋 TAREAS INICIALES EN EL SISTEMA:");
            System.out.println("─".repeat(60));
            List<Tarea> tareasIniciales = tareaService.listarTodas();
            mostrarListaDeTareas(tareasIniciales);
            System.out.println("─".repeat(60));
            System.out.println("Total: " + tareasIniciales.size() + " tareas");
            
            // ═══════════════════════════════════════════════════════════
            // 3️⃣ AGREGAR NUEVAS TAREAS
            // ═══════════════════════════════════════════════════════════
            System.out.println("\n\n➕ AGREGANDO NUEVAS TAREAS:");
            System.out.println("─".repeat(60));
            
            tareaService.agregarTarea(
                "Configurar entorno de integración continua", 
                Prioridad.ALTA
            );
            
            tareaService.agregarTarea(
                "Diseñar mockups de la interfaz de usuario", 
                Prioridad.MEDIA
            );
            
            tareaService.agregarTarea(
                "Actualizar documentación técnica", 
                Prioridad.BAJA
            );
            
            System.out.println("─".repeat(60));
            
            // ═══════════════════════════════════════════════════════════
            // 4️⃣ LISTAR TAREAS PENDIENTES
            // ═══════════════════════════════════════════════════════════
            System.out.println("\n\n⏳ TAREAS PENDIENTES:");
            System.out.println("─".repeat(60));
            List<Tarea> pendientes = tareaService.listarPendientes();
            mostrarListaDeTareas(pendientes);
            System.out.println("─".repeat(60));
            System.out.println("Total pendientes: " + pendientes.size());
            
            // ═══════════════════════════════════════════════════════════
            // 5️⃣ MARCAR TAREAS COMO COMPLETADAS
            // ═══════════════════════════════════════════════════════════
            System.out.println("\n\n✅ COMPLETANDO TAREAS:");
            System.out.println("─".repeat(60));
            
            // Marcar las primeras 3 tareas como completadas
            tareaService.marcarComoCompletada(1L);
            tareaService.marcarComoCompletada(2L);
            tareaService.marcarComoCompletada(3L);
            
            System.out.println("─".repeat(60));
            
            // ═══════════════════════════════════════════════════════════
            // 6️⃣ MOSTRAR ESTADÍSTICAS
            // ═══════════════════════════════════════════════════════════
            System.out.println("\n");
            tareaService.mostrarEstadisticasEnConsola();
            
            // ═══════════════════════════════════════════════════════════
            // 7️⃣ LISTAR TAREAS COMPLETADAS
            // ═══════════════════════════════════════════════════════════
            System.out.println("\n\n✔️  TAREAS COMPLETADAS:");
            System.out.println("─".repeat(60));
            List<Tarea> completadas = tareaService.listarCompletadas();
            mostrarListaDeTareas(completadas);
            System.out.println("─".repeat(60));
            System.out.println("Total completadas: " + completadas.size());
            
            // ═══════════════════════════════════════════════════════════
            // 8️⃣ MENSAJE DE DESPEDIDA
            // ═══════════════════════════════════════════════════════════
            System.out.println(mensajeService.despedida(tareaService.getNombreApp()));
            
            logger.info("Flujo de demostración completado exitosamente");
            
        } catch (Exception e) {
            logger.error("Error durante la ejecución de la aplicación", e);
            System.err.println("\n❌ ERROR: " + e.getMessage());
            System.err.println("Revisa los logs para más detalles.");
        }
    }
    
    /**
     * Método auxiliar para mostrar una lista de tareas en formato legible
     * 
     * @param tareas Lista de tareas a mostrar
     */
    private void mostrarListaDeTareas(List<Tarea> tareas) {
        if (tareas.isEmpty()) {
            System.out.println("  (No hay tareas en esta categoría)");
            return;
        }
        
        for (Tarea tarea : tareas) {
            System.out.println("  " + tarea);
        }
    }
}
