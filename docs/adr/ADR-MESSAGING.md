# ADR-MESSAGING: Elección de broker de eventos

## Context
El sistema requiere un mecanismo de mensajería para:
- Propagar eventos de creación de Tweets hacia otros componentes (ej. construcción de timelines).
- Desacoplar productores (servicio de Tweets) y consumidores (servicio de Timeline u otros futuros).
- Soportar procesamiento asincrónico de alto volumen, garantizando orden y entrega confiable.
- Escalar horizontalmente la cantidad de consumidores sin afectar el throughput de escritura.
- Permitir la futura incorporación de otros eventos del dominio (seguimientos, métricas, notificaciones).

Se necesita un broker que brinde **alta disponibilidad, tolerancia a fallos, y throughput elevado**, con capacidad de persistir los mensajes y permitir **reprocesamiento (replay)** en caso de errores.

## Decision
Se eligió **Apache Kafka** como broker de eventos principal.

## Consequences

**Ventajas:**
- Altísimo throughput y baja latencia para grandes volúmenes de eventos.
- Persistencia en disco con retención configurable: permite reprocesar eventos en caso de errores o nuevos consumidores.
- Soporte nativo de **particiones** y **consumidores en grupo** para escalar horizontalmente los consumidores.
- Garantiza orden dentro de una partición, útil para mantener orden cronológico de los tweets por usuario.
- Ecosistema maduro y ampliamente adoptado (librerías, conectores, monitoreo, soporte cloud-managed).
- Buena integración con Quarkus a través de `smallrye-reactive-messaging`.

**Desventajas / riesgos:**
- Mayor complejidad operativa que brokers simples (requiere Zookeeper/KRaft, monitoreo, tuning de particiones).
- Requiere más recursos de infraestructura que alternativas ligeras como RabbitMQ.
- Añade complejidad inicial si el volumen de eventos aún es bajo.

## Alternatives considered
- **Sin broker (comunicación síncrona):** descartado porque acopla servicios, aumenta la latencia y no escala para cargas elevadas de escritura.
- **RabbitMQ / ActiveMQ:** descartados porque priorizan patrones de mensajería tradicionales (colas, routing) pero no escalan tan bien en throughput ni permiten replay de eventos antiguos fácilmente.

