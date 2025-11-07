package com.utn.tareas;

import com.utn.tareas.service.TareaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test básico para verificar que el contexto de Spring se carga correctamente
 */
@SpringBootTest
class TareasApplicationTests {

    @Autowired
    private TareaService tareaService;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
        assertNotNull(tareaService);
    }

}
