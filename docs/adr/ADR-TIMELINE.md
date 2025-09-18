# ADR-TIMELINE: Estrategia de construcción del timeline

## Context
El sistema necesita construir el timeline de cada usuario, que consiste en una lista paginada de tweets publicados por los usuarios que sigue.  
Los requisitos principales son:

- Lecturas muy frecuentes (read-heavy) y con baja latencia.
- Necesidad de paginar eficientemente timelines potencialmente grandes.
- Soporte para millones de usuarios y alto volumen de tweets concurrentes.
- Consistencia eventual aceptable (no es crítico ver tweets en tiempo real al instante exacto).

Se evaluaron dos estrategias clásicas para timelines: **fan-out on read** y **fan-out on write**.

## Decision
Se decidió implementar el timeline con un enfoque de **fan-out on write**:

- Cada vez que un usuario publica un tweet, el servicio de Tweets emite un evento en Kafka (`tweets-topic`).
- Un consumidor (Timeline Service) procesa el evento y replica el tweet en las colas/listas de timeline de todos los seguidores del autor.
- Los endpoints de lectura (`GET /timeline`) solo leen y paginan los tweets precalculados del timeline del usuario.

## Consequences

**Ventajas:**
- Las lecturas son extremadamente rápidas, ya que no requieren joins ni merges en tiempo real.
- Escala horizontalmente: las escrituras se distribuyen en particiones de Kafka y en múltiples consumidores.
- Reduce la carga sobre la base de datos relacional, que no necesita resolver relaciones complejas en cada request de timeline.
- Permite cachear timelines por usuario de forma simple en Redis si fuese necesario en el futuro.

**Desventajas / riesgos:**
- Mayor costo en escrituras: cada tweet se replica en tantos timelines como seguidores tenga el autor.
- En usuarios con millones de seguidores se podría generar una carga muy alta de escritura (caso edge). Se asume que no existen estos usuarios (ver: [business.txt](../../business.txt)).
- No refleja inmediatamente los cambios en las relaciones de follow/unfollow (requiere lógica extra si se quiere resolver). Se asume que no es necesario (ver: [business.txt](../../business.txt)).

## Alternatives considered
- **Fan-out on read:** construir el timeline dinámicamente en cada request haciendo joins entre Tweets y Follows.
    - Más simple conceptualmente, pero muy costoso en lectura y difícil de escalar con muchos usuarios. Es útil solo en casos edge donde existen usuarios con millones de seguidores.
- **Modelo híbrido (capa de búsqueda):** almacenar tweets crudos y construir timelines on-demand con ElasticSearch.
    - Permite búsquedas avanzadas, pero agrega latencia y complejidad innecesaria para este MVP.
