# ADR-DB: Elección de base de datos

## Context
El sistema requiere persistencia para manejar:
- Entidades con relaciones bien definidas.
- Consultas complejas con joins, ordenamientos y filtros.
- Alta velocidad de lectura para soportar endpoints con gran volumen de tráfico.
- Escalabilidad horizontal futura (sharding, replicación, particionado).
- Necesidad de integridad referencial para garantizar consistencia entre entidades (Tweets, Users, Follows, Timeline).
- Capacidad de auditar datos y ejecutar consultas ad-hoc en producción ante incidentes.

## Decision
Se eligió **PostgreSQL** como base de datos relacional principal en esta primera etapa. 
Para no aumentar complejidad, no se realziará migracioens. Se autogenera´ra por orm update.

## Consequences

**Ventajas:**
- Soporta de forma robusta tipos de datos avanzados (`JSONB`, arrays, enums) que podrían ser útiles en el futuro para almacenar metadatos.
- Excelente soporte de integridad referencial y constraints a nivel de base de datos.
- Soporte de índices avanzados (GIN, BRIN, índices compuestos) que permiten optimizar consultas de lectura intensiva.
- Buen rendimiento en lecturas concurrentes gracias a su arquitectura MVCC (Multiversion Concurrency Control).
- Escalabilidad horizontal a través de replicación nativa (read replicas) y soporte de particiones.
- Comunidad muy activa y ecosistema maduro (ORMs, migradores, herramientas de backup y monitoreo).
- Permite ejecutar consultas manuales en producción en caso de incidentes para diagnosticar problemas.
- Compatible con múltiples estrategias de despliegue (Docker, Kubernetes, cloud-managed DBs).

**Desventajas / riesgos:**
- Consumo de memoria algo mayor que MySQL en configuraciones pequeñas.
- Mayor complejidad al realizar cambios frecuentes en el esquema (requiere disciplina de migraciones y versionado).
- Para soportar escrituras de muy alto volumen se necesitará diseñar estrategias de sharding manual o introducir otras bases complementarias.

## Alternatives considered
- **Bases de datos tipo documento (MongoDB):** descartado por tener soporte limitado para joins, queries complejas y ordenamientos eficientes, además de requerir mayor esfuerzo para mantener consistencia entre colecciones.
- **Otras bases de datos NoSQL (DynamoDB, Cassandra):** descartado porque, aunque ofrecen alto throughput y escalabilidad automática, no permiten búsquedas complejas ni relaciones. Podrían evaluarse en el futuro si se dividen los componentes en microservicios especializados (por ejemplo, timeline cacheado o lectura masiva).
