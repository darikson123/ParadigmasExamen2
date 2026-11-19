# Examen2 — Registro de Exámenes de Sangre (Arquitectura Hexagonal + patrones GoF)

Aplicación de escritorio JavaFX para registrar exámenes de sangre (código, paciente, costo, tipo de sangre y factor RH), listarlos y guardarlos/recuperarlos desde un archivo.

Este repositorio es un fork de [WolframDev/ParadigmasExamen2](https://github.com/WolframDev/ParadigmasExamen2), refactorizado hacia **Arquitectura Hexagonal (Ports and Adapters)** e incorporando tres patrones GoF: **Builder** (creacional), **Decorator** (estructural) y **Observer** (comportamiento).

Los diagramas, informes, el análisis de SonarQube y el video están en la [Wiki del repositorio](https://github.com/darikson123/ParadigmasExamen2/wiki).

## Funcionalidades

| Botón | Operación |
|---|---|
| Guardar | Registra un examen validando las reglas de negocio y que el código no esté repetido. |
| Listar | Muestra los exámenes registrados en el área de mensajes. |
| Serializar | Guarda los exámenes registrados en el archivo `examenes.dat`. |
| Deserializar | Recupera los exámenes desde `examenes.dat` y reemplaza los que están en memoria. |
| Limpiar | Limpia los campos del formulario. |

## Arquitectura

```
co.edu.poli.Examen2
├── dominio.modelo                 Entidad ExamenSangre (+ Builder), TipoSangre, FactorRH
├── aplicacion
│   ├── puertos.entrada            Interfaces de los casos de uso (Registrar, Listar, Serializar, Deserializar)
│   ├── puertos.salida             ExamenRepositorioPuerto, ArchivoExamenesPuerto
│   ├── casosuso                   Implementación de los casos de uso
│   ├── decorador                  Decorator sobre ExamenRepositorioPuerto (código único)
│   └── observador                 Observer: PublicadorEventos y ObservadorExamen
└── infraestructura
    ├── entrada.javafx             App (arranque y ensamblaje) y PrimaryController (adaptador JavaFX)
    └── salida
        ├── memoria                RepositorioExamenesMemoria
        └── archivo                SerializadorExamenesArchivo (examenes.dat)
```

Las dependencias apuntan siempre hacia el núcleo: el dominio y la aplicación no dependen de JavaFX ni de la forma en que se persisten los datos. `App` es la única clase que conoce las implementaciones concretas.

### Patrones GoF

- **Builder** (`ExamenSangre.Builder`): construye el examen paso a paso y valida las reglas de negocio en `build()`, de modo que nunca existe un examen inválido.
- **Decorator** (`RepositorioExamenDecorador`, `CodigoUnicoRepositorioDecorador`): agrega la regla de código único a cualquier implementación del repositorio sin modificar el adaptador.
- **Observer** (`PublicadorEventos`, `ObservadorExamen`): los casos de uso publican lo ocurrido y el controlador JavaFX lo muestra, sin que la aplicación conozca la interfaz gráfica.

## Requisitos

- JDK 11 o superior (probado con JDK 21).
- Apache Maven 3.6.3 o superior.
- Git.

Las dependencias (JavaFX 13, JUnit 5) se descargan automáticamente con Maven.

## Instalación

```bash
git clone https://github.com/darikson123/ParadigmasExamen2.git
cd ParadigmasExamen2/Examen2
mvn clean install
```

## Ejecución

Desde la carpeta `Examen2`:

```bash
mvn clean javafx:run
```

El archivo `examenes.dat` se crea en la carpeta desde la que se ejecuta la aplicación al presionar **Serializar**.

## Pruebas unitarias

```bash
mvn clean test
```

Las pruebas (JUnit 5) cubren el dominio, los casos de uso, los patrones Decorator y Observer y los adaptadores de salida. El reporte de cobertura de JaCoCo se genera con:

```bash
mvn clean verify
```

y queda en `Examen2/target/site/jacoco/index.html`.

## Documentación JavaDoc

```bash
mvn javadoc:javadoc
```

La documentación queda en `Examen2/target/site/apidocs/index.html`.

## Análisis con SonarQube

Con SonarCloud (o un servidor SonarQube), desde la carpeta `Examen2`:

```bash
mvn clean verify sonar:sonar \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.organization=<organizacion> \
  -Dsonar.projectKey=<clave-del-proyecto> \
  -Dsonar.token=<token>
```

Para un servidor local, usar `-Dsonar.host.url=http://localhost:9000` y omitir `sonar.organization`. El resumen del análisis está en la Wiki.

## Declaración de uso de IA

| Herramienta | Actividades en las que se usó |
|---|---|
| Claude (Anthropic) | Apoyo en el análisis del proyecto original, propuesta de la arquitectura hexagonal y de los patrones GoF, elaboración de diagramas, escritura de código, pruebas unitarias, JavaDoc y este README. |

Todo el contenido generado con apoyo de IA fue revisado, ejecutado y validado por el autor.

## Autor

Darikson Nikolas Camargo Ayala
