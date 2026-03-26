# Informe de Desarrollo Técnico Sistema de Gestión de Destinos Turísticos DP2DSM

**Autor** 
Efrain Antonio Cortez Claros  
Ingeniería en Ciencias de la Computación  
Universidad Don Bosco

## 1. Introducción
El presente proyecto, denominado DP2DSM, consiste en una aplicación móvil nativa para la plataforma Android diseñada para optimizar la gestión de catálogos en agencias de viajes. La solución permite a los usuarios administrativos registrar, visualizar, actualizar y eliminar destinos turísticos de manera eficiente, garantizando la persistencia de la información mediante servicios en la nube.

## 2. Descripción Funcional
La aplicación integra un ciclo completo de gestión de datos bajo el estándar CRUD, desglosado en las siguientes capacidades:

**Control de Acceso y Seguridad**
Implementación de un módulo de autenticación de usuarios que restringe las funciones de administración a personal autorizado. El sistema valida credenciales en tiempo real y gestiona el estado de la sesión de manera persistente.

**Gestión de Inventario Turístico**
* Registro de nuevos destinos incluyendo metadatos como nombre, precio, descripción detallada y origen geográfico.
* Galería dinámica que recupera y renderiza imágenes desde servidores remotos.
* Interfaz de edición para la actualización de tarifas o descripciones de paquetes existentes.
* Sistema de remoción de registros con protocolos de confirmación para integridad de la base de datos.

## 3. Especificaciones Técnicas
Para la construcción de este sistema se han empleado tecnologías alineadas con los estándares actuales de la industria del desarrollo móvil:

**Entorno de Desarrollo y Lenguaje**
* Kotlin: Lenguaje principal de programación, utilizado por su robustez y manejo eficiente de la memoria.
* View Binding: Técnica empleada para la vinculación de componentes de interfaz, reduciendo errores de ejecución y mejorando el rendimiento.

**Infraestructura de Backend**
* Firebase Authentication: Servicio especializado para la gestión segura de identidades.
* Firebase Realtime Database: Motor de base de datos NoSQL para el almacenamiento de datos en formato JSON y sincronización instantánea entre clientes.

**Librerías Integradas**
* Glide v4.16.0: Framework avanzado para la gestión de peticiones HTTP de imágenes, manejo de caché y optimización de bitmaps.
* Material Design 1.13.0: Biblioteca de componentes visuales para garantizar una interfaz de usuario profesional y estandarizada.

## 4. Estructura de Arquitectura
El proyecto sigue una estructura organizada por actividades y modelos de datos:

* LoginActivity: Punto de entrada que gestiona la seguridad del sistema.
* RegisterActivity: Módulo para la creación de nuevas cuentas de agentes.
* MainActivity: Panel principal que despliega el catálogo mediante un RecyclerView adaptativo.
* Add/EditDestinoActivity: Formularios especializados para la manipulación de la entidad Destino.

## 5. Configuración del Entorno
Para ejecutar el proyecto en un entorno de pruebas:
1. Clonar el repositorio desde la rama principal.
2. Vincular el proyecto con una instancia de Firebase desde el IDE Android Studio.
3. Colocar el archivo de configuración google-services.json en el directorio raíz del módulo app.
4. Ejecutar la sincronización de Gradle para descargar las dependencias listadas en el archivo build.gradle.

## 6. Conclusión
El desarrollo de la aplicación DP2DSM demuestra la integración efectiva de arquitecturas móviles modernas con servicios de computación en la nube. Este proyecto cumple con los requerimientos técnicos de escalabilidad y usabilidad profesional exigidos en el ámbito académico de la ingeniería.
Enlace del video: https://drive.google.com/file/d/1uUUrl7FUfK-HDoLJ4SJO7kNk1jPPNJyy/view?usp=sharing
