# ADR-CACHE: Elección de sistema de caché

## Context
El sistema necesita optimizar el rendimiento de operaciones de lectura intensiva, especialmente:

- Consultas frecuentes a entidades inmutables o que cambian poco (Tweets, relaciones de Follow).
- Reducción de carga sobre la base de datos relacional para evitar cuellos de botella.
- Minimizar la latencia de respuesta en endpoints con alto volumen de tráfico.
- Posibilidad de cachear resultados agregados o timelines precomputados en el futuro.

Se requiere un sistema de caché que ofrezca **baja latencia, alta disponibilidad y soporte para escalabilidad horizontal.**

## Decision
Se eligió **Redis** como sistema de caché distribuido.

## Consequences

**Ventajas:**
- Tiempo de respuesta extremadamente bajo (en memoria) y throughput muy alto.
- Soporte nativo para estructuras de datos útiles (listas, sets, sorted sets, hashes) para modelar relaciones simples como timelines o seguidores.
- TTL (time-to-live) configurable para invalidar automáticamente datos obsoletos.
- Alta disponibilidad mediante cluster y replicación.
- Ecosistema maduro, ampliamente adoptado, con buena integración en Quarkus y clientes oficiales.

**Desventajas / riesgos:**
- Los datos se almacenan en memoria, por lo que puede ser costoso para grandes volúmenes si no se diseñan bien las políticas de expiración.
- No es fuente de verdad: requiere estrategias de invalidación o expiración coherentes para evitar servir datos obsoletos.
- Añade complejidad operativa (monitoring, cluster, sizing) si el uso escala mucho.

## Alternatives considered
- **Caffeine (in-memory local cache):** descartado porque no permite compartir datos entre instancias del servicio ni escalar horizontalmente.
- **Memcached:** descartado porque ofrece un conjunto de funcionalidades más limitado, un ecosistema menos activo y menor integración con Quarkus.
- **Sin caché:** descartado porque generaría alta carga en la base de datos y aumentaría la latencia en endpoints críticos.
