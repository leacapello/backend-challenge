# ADR-OBSERVABILITY: Estrategia de observabilidad

## Context
El sistema necesita contar con visibilidad operacional desde el inicio, para poder:

- Diagnosticar fallas y cuellos de botella en producción.
- Correlacionar requests entre microservicios.
- Medir el rendimiento técnico y la actividad funcional (número de tweets creados, seguidores, etc.).
- Permitir monitoreo continuo de la salud de los servicios y detección proactiva de incidentes.

## Decision
Se decidió implementar un stack de observabilidad basado en **logs estructurados con trazas distribuidas (OpenTelemetry)** y **métricas técnicas expuestas para Prometheus/Grafana**.

## Consequences

**Logs y trazas:**
- Se implementó logging estructurado utilizando MDC (Mapped Diagnostic Context) para incluir `traceId`, `spanId`, `userId` y otros metadatos relevantes en cada log.
- Se integró OpenTelemetry (OTel) para generación y propagación automática de trazas distribuidas entre servicios.
- Los logs permiten correlacionar todos los eventos de un request de extremo a extremo, incluso entre microservicios distintos.
- Se facilita la integración futura con sistemas de log centralizado como Loki, ELK o Cloud Logging.

**Métricas:**
- Los servicios exponen el endpoint `/q/metrics` en formato Prometheus para ser scrapeado por Prometheus y visualizado en Grafana.
- Actualmente se recolectan métricas técnicas estándar (uso de memoria, threads, tiempo de respuesta, etc.).
- Aún no se implementaron métricas de negocio personalizadas, pero podrían añadirse fácilmente utilizando OpenTelemetry Metrics.  
  Ejemplos posibles: cantidad de tweets creados por minuto, cantidad de follows/unfollows, tamaño promedio de timeline, etc.
- Esto permitirá crear dashboards que combinen métricas técnicas y de negocio para tener una visión completa del estado del sistema.

**Configuración requerida:**
- Para habilitar la instrumentación en Quarkus en entornos reales, es necesario activar las siguientes propiedades (actualmente en `false` en desarrollo):
  ```properties
  quarkus.otel.enabled=true
  quarkus.otel.metrics.enabled=true
