# 🚀 Sistema de Gestión de Tareas - Spring Boot

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://openjdk.java.net/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.3-green.svg)](https://gradle.org/)

## 📋 Descripción

Sistema de gestión de tareas implementado en **Spring Boot 3.x** que demuestra los fundamentos del framework:

- ✅ **Inyección de Dependencias** por constructor
- ✅ **Perfiles de configuración** (dev/prod)
- ✅ **Configuración externa** mediante properties
- ✅ **Arquitectura en capas** desacoplada
- ✅ **Persistencia en memoria** con repositorio personalizado
- ✅ **Servicios condicionales** según perfil activo

## 🏗️ Arquitectura del Proyecto

```
com.utn.tareas/
├── model/                    # Capa de Dominio
│   ├── Tarea.java           # Entidad de tarea con Lombok
│   └── Prioridad.java       # Enum de prioridades
│
├── repository/              # Capa de Persistencia
│   ├── TareaRepository.java       # Interfaz del repositorio
│   └── TareaRepositoryImpl.java   # Implementación en memoria
│
├── service/                 # Capa de Negocio
│   ├── TareaService.java          # Lógica de negocio
│   ├── MensajeService.java        # Interfaz de mensajería
│   ├── MensajeDevService.java     # Mensajes para desarrollo
│   └── MensajeProdService.java    # Mensajes para producción
│
└── TareasApplication.java   # Clase principal con CommandLineRunner
```

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.5.7** - Framework principal
- **Java 17** - Lenguaje de programación
- **Lombok** - Reducción de código boilerplate
- **Gradle** - Herramienta de construcción
- **SLF4J** - Sistema de logging

## ⚙️ Configuración por Perfiles

### Perfil de Desarrollo (`dev`)

```properties
app.max-tareas=10
app.mostrar-estadisticas=true
logging.level.com.utn.tareas=DEBUG
```

**Características:**
- Límite de 10 tareas
- Estadísticas habilitadas
- Logging detallado (DEBUG)
- Mensajes amistosos y descriptivos

### Perfil de Producción (`prod`)

```properties
app.max-tareas=1000
app.mostrar-estadisticas=false
logging.level.com.utn.tareas=ERROR
```

**Características:**
- Límite de 1000 tareas
- Estadísticas deshabilitadas
- Logging mínimo (ERROR)
- Mensajes concisos y profesionales

## 🚀 Instrucciones de Ejecución

### Opción 1: Gradle (Línea de Comandos)

#### Ejecutar con perfil DEV (por defecto)
```bash
.\gradlew.bat bootRun
```

#### Ejecutar con perfil PROD
```bash
.\gradlew.bat bootRun --args='--spring.profiles.active=prod'
```

### Opción 2: IntelliJ IDEA

1. **Importar el proyecto:**
   - File → Open → Seleccionar la carpeta del proyecto
   - IntelliJ detectará automáticamente el proyecto Gradle

2. **Ejecutar con perfil DEV:**
   - Abrir `TareasApplication.java`
   - Click derecho → Run 'TareasApplication'

3. **Ejecutar con perfil PROD:**
   - Run → Edit Configurations
   - En "VM options" agregar: `-Dspring.profiles.active=prod`
   - O en "Program arguments": `--spring.profiles.active=prod`
   - Click OK y ejecutar

### Opción 3: Eclipse

1. **Importar el proyecto:**
   - File → Import → Gradle → Existing Gradle Project
   - Seleccionar la carpeta del proyecto

2. **Ejecutar con perfil DEV:**
   - Click derecho en `TareasApplication.java`
   - Run As → Java Application

3. **Ejecutar con perfil PROD:**
   - Run → Run Configurations
   - Seleccionar la configuración de TareasApplication
   - En "Arguments" → "Program arguments": `--spring.profiles.active=prod`
   - Apply y Run

## 🔄 Cambiar entre Perfiles

### Método 1: Archivo `application.properties`

Editar `src/main/resources/application.properties`:

```properties
# Para desarrollo
spring.profiles.active=dev

# Para producción
spring.profiles.active=prod
```

### Método 2: Variable de Entorno

**Windows (PowerShell):**
```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
.\gradlew.bat bootRun
```

**Linux/Mac:**
```bash
export SPRING_PROFILES_ACTIVE=prod
./gradlew bootRun
```

### Método 3: Argumento de Línea de Comandos

```bash
.\gradlew.bat bootRun --args='--spring.profiles.active=prod'
```

## 📊 Salida de Ejemplo (Perfil DEV)

```
╔════════════════════════════════════════════════════════════╗
║                                                            ║
║         🚀 BIENVENIDO AL MODO DESARROLLO 🚀                ║
║                                                            ║
║  Aplicación: Gestor de Tareas UTN                         ║
║  → Perfil activo: DEV                                      ║
║  → Logging detallado activado                              ║
║  → Estadísticas habilitadas                                ║
║  → Límite de tareas: 10                                    ║
╚════════════════════════════════════════════════════════════╝

📋 TAREAS INICIALES EN EL SISTEMA:
────────────────────────────────────────────────────────────
  [ID: 1] ○ PENDIENTE | Implementar módulo de autenticación | Prioridad: ALTA
  [ID: 2] ○ PENDIENTE | Revisar documentación de Spring Boot | Prioridad: MEDIA
  ...

┌─────────────────────────────────────────────────────┐
│           📊 ESTADÍSTICAS DEL SISTEMA              │
├─────────────────────────────────────────────────────┤
│  Total de tareas:             8 / 10              │
│  Tareas completadas:          3                    │
│  Tareas pendientes:           5                    │
│  Progreso:                    37.5%                │
└─────────────────────────────────────────────────────┘
```

## 📦 Compilación

```bash
# Compilar el proyecto
.\gradlew.bat clean build

# Ejecutar tests
.\gradlew.bat test

# Generar JAR ejecutable
.\gradlew.bat bootJar
```

El JAR se generará en: `build/libs/SistemaDeGestionDeTareas-1.0.0.jar`

Para ejecutarlo:
```bash
java -jar build/libs/SistemaDeGestionDeTareas-1.0.0.jar
```

## 🎯 Funcionalidades Implementadas

### Repositorio (TareaRepository)
- ✅ Listar todas las tareas
- ✅ Guardar nuevas tareas
- ✅ Buscar tarea por ID
- ✅ Eliminar tareas
- ✅ Inicialización con datos de ejemplo

### Servicio (TareaService)
- ✅ Agregar tareas con validación
- ✅ Listar tareas (todas/pendientes/completadas)
- ✅ Marcar tareas como completadas
- ✅ Obtener estadísticas detalladas
- ✅ Validación de límite de tareas según perfil
- ✅ Inyección de configuración externa

### Mensajería (MensajeService)
- ✅ Mensajes personalizados por perfil
- ✅ Bienvenida y despedida según contexto
- ✅ Activación condicional con @Profile

## 🧪 Casos de Uso Demostrados

El flujo de ejecución (`CommandLineRunner`) demuestra:

1. **Mensaje de bienvenida** según perfil activo
2. **Listado de tareas iniciales** del repositorio
3. **Agregación de nuevas tareas** con validaciones
4. **Filtrado de tareas pendientes**
5. **Marcado de tareas como completadas**
6. **Generación de estadísticas** (solo en dev)
7. **Listado de tareas completadas**
8. **Mensaje de despedida** personalizado

## 📝 Notas Importantes

- El proyecto utiliza **inyección por constructor**, no por campo (best practice)
- Todas las clases tienen **anotaciones Spring apropiadas** (@Service, @Repository, etc.)
- Los **logs están configurados por perfil** (DEBUG en dev, ERROR en prod)
- La **configuración es externa** y fácilmente modificable
- El código está **completamente comentado** y documentado

## 👨‍💻 Autor

**Sistema de Gestión de Tareas UTN**  
Proyecto demostrativo de Spring Boot - Java 17

---

**¡Proyecto listo para ejecutar! 🎉**
