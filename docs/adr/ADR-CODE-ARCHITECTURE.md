# ADR-CODE-ARCHITECTURE: Elección de arquitectura del código

## Context
El sistema debe escalar funcional y técnicamente, por lo que requiere una arquitectura que:

- Permita aislar cada dominio funcional (Tweets, Follows, Timeline) para escalar de forma independiente.
- Mantenga el **dominio aislado del framework y de la infraestructura** para facilitar el testeo, la mantenibilidad y el reemplazo de componentes.
- Desacople los flujos de lectura (read-heavy) y escritura (write-focused) para optimizar el rendimiento.
- Soporte evolución futura hacia microservicios si el volumen de tráfico lo requiere.
- Soporte la segmentación en el despliegue de escrituras y lecturas por separado para mejorar escalabilidad y rendimiento.
- Evite la erosión de la arquitectura a medida que crece el sistema.

## Decision
Se adoptó una arquitectura basada en:

- **Hexagonal Architecture (Ports & Adapters)** para separar el dominio central de los detalles técnicos.
- **Vertical Slicing + Bounded Contexts** para organizar el código por dominios funcionales en lugar de capas técnicas compartidas, con desacoplamiento total entre slices.
- **CQRS (Command Query Responsibility Segregation)** para desacoplar operaciones de lectura y escritura con distintos flujos de aplicación.
- **Platform Slice**: un slice transversal que contiene lógica y configuraciones comunes (frameworks, logging, seguridad, observabilidad, etc.).

## Consequences

**Hexagonal Architecture:**
- El dominio contiene las reglas de negocio puras (entidades, value objects, invariantes).
- Define interfaces (puertos) que la infraestructura implementa (adaptadores).
- Los adaptadores de entrada (REST) y de salida (DB, Kafka, Redis) dependen del dominio, pero no al revés.
- Permite testear el dominio sin dependencias externas.

**Vertical Slicing + Bounded Contexts:**
- Cada módulo funcional (ej. `tweet`, `follower`, `timeline`) tiene su propio conjunto de capas: `domain`, `application`, `infrastructure`.
- Evita módulos “anémicos” y dependencias cruzadas innecesarias.
- Facilita la evolución hacia microservicios si algún bounded context necesita escalar por separado.
- Favorece equipos que puedan trabajar en paralelo en distintos contextos sin conflictos.

**CQRS:**
- Cada bounded context separa comandos (escritura) y queries (lectura) en `application/command` y `application/query`.
- Los comandos modifican el estado y usan repositorios de escritura.
- Las queries solo leen estado y usan repositorios optimizados para lectura.
- Facilita optimizar de forma independiente el modelo de lectura y el de escritura (incluso con distintas fuentes de datos en el futuro).

**Ventajas:**
- Alta cohesión y bajo acoplamiento: los cambios en un contexto no rompen otros.
- Código más mantenible y testeable.
- Permite escalar funcionalidades específicas sin reescribir el resto.
- Facilita introducir nuevas tecnologías en la infraestructura sin tocar el dominio.

**Desventajas / riesgos:**
- Mayor complejidad inicial y curva de aprendizaje más alta.
- Más clases y estructura de carpetas, lo que puede parecer sobreingeniería en etapas iniciales.

## Alternatives considered
- **Arquitectura en capas clásica (controller-service-repository):** descartada porque tiende a generar capas anémicas y alto acoplamiento transversal, lo que complica el crecimiento del sistema.
- **Monolito modular sin separación por dominio:** descartado porque dificulta escalar componentes de forma independiente y genera acoplamientos fuertes entre funcionalidades.
