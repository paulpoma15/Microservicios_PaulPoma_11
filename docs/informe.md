# Informe Técnico – Angoma Tours Microservices

## Team 04

* Paul Poma Ayquipa
* Luciana Ruiz

---

# Parte A – Conceptos

## 1. ¿Qué es Micrometer y por qué se usa en lugar de la librería directa de Prometheus?

Micrometer es una librería de observabilidad utilizada en aplicaciones Spring Boot para recolectar métricas de manera independiente al sistema de monitoreo utilizado.

Se utiliza en lugar de la librería directa de Prometheus porque funciona como una capa de abstracción. Gracias a ello, la aplicación puede enviar métricas a Prometheus, Grafana, Datadog u otros sistemas sin modificar el código principal.

En nuestro proyecto, Micrometer fue utilizado junto con Spring Boot Actuator para registrar métricas del servicio de reservas (Booking Service).

---

## 2. ¿Cuál es la diferencia entre Counter, Gauge y Timer?

### Counter

Mide eventos acumulativos que solamente incrementan.

Ejemplo en nuestro proyecto:

```java
bookingCounter.increment();
```

Se ejecuta cada vez que se crea una nueva reserva.

### Gauge

Representa un valor que puede aumentar o disminuir.

Ejemplo:

Cantidad actual de paquetes turísticos disponibles.

```java
Gauge.builder("packages.available", packageList, List::size)
```

### Timer

Mide la duración de una operación.

Ejemplo:

Tiempo que tarda Booking Service en consultar la disponibilidad de un paquete mediante WebClient.

```java
Timer.Sample sample = Timer.start();
```

---

## 3. ¿Qué es un ServiceMonitor y cómo sabe el Prometheus Operator qué monitorear?

Un ServiceMonitor es un recurso personalizado de Kubernetes utilizado por Prometheus Operator.

Su función es indicar qué servicios deben ser monitoreados y en qué endpoint se encuentran las métricas.

Prometheus Operator detecta automáticamente los ServiceMonitors mediante etiquetas (labels) configuradas en el cluster.

De esta manera, Prometheus descubre los endpoints `/actuator/prometheus` de nuestros microservicios.

---

## 4. ¿Cuál es la diferencia entre liveness probe y readiness probe?

### Liveness Probe

Determina si la aplicación sigue viva.

Si falla varias veces consecutivas, Kubernetes reinicia el contenedor.

Ejemplo:

```yaml
livenessProbe:
  httpGet:
    path: /actuator/health
    port: 8080
```

### Readiness Probe

Determina si la aplicación está preparada para recibir tráfico.

Si falla, Kubernetes deja de enviar solicitudes al pod.

Ejemplo:

```yaml
readinessProbe:
  httpGet:
    path: /actuator/health
    port: 8080
```

---

## 5. ¿Por qué es necesario apuntar Docker al daemon de Minikube antes de construir imágenes?

Porque Kubernetes dentro de Minikube utiliza su propio motor Docker.

Si las imágenes se construyen en Docker Desktop y no dentro del daemon de Minikube, Kubernetes no podrá encontrarlas localmente.

Por ello se utiliza:

```bash
minikube docker-env
```

y posteriormente:

```bash
eval $(minikube docker-env)
```

---

## 6. ¿Qué ocurre si no configuras el selector de ServiceMonitors en el values.yaml del chart?

Prometheus Operator no podrá descubrir automáticamente los ServiceMonitors.

Como consecuencia:

* No aparecerán targets.
* No se recopilarán métricas.
* Los dashboards de Grafana mostrarán datos vacíos.
* Las alertas no funcionarán correctamente.

---

# Parte B – PromQL

## 1. Tasa de requests por minuto de Booking Service en los últimos 5 minutos

```promql
rate(http_server_requests_seconds_count{application="booking-service"}[5m]) * 60
```

---

## 2. Latencia p95 del endpoint más crítico

Endpoint:

```text
/bookings/check-package/{id}
```

Query:

```promql
histogram_quantile(
  0.95,
  sum(rate(http_server_requests_seconds_bucket[5m])) by (le)
)
```

---

## 3. Estado UP/DOWN de ambos servicios

```promql
up{job=~"booking-service|tour-package-service"}
```

Valor:

* 1 = UP
* 0 = DOWN

---

## 4. Query de negocio

Pregunta:

¿Cuántas reservas fueron creadas?

```promql
bookings_created_total
```

Esta métrica permite conocer el volumen de reservas registradas en el sistema.

---

# Parte C – Evidencias de Casuísticas

## Casuística 1 – Caída de Booking Service

### Contexto

Se simuló la caída del servicio Booking Service para verificar el comportamiento de Kubernetes y Prometheus.

### Activación

```bash
kubectl delete pod booking-deployment-xxxxx
```

### Evidencia

Insertar captura de Grafana mostrando caída temporal.

### Resolución

Kubernetes recreó automáticamente el pod.

Verificación:

```bash
kubectl get pods
```

### Lección aprendida

Kubernetes proporciona alta disponibilidad mediante recuperación automática.

---

## Casuística 2 – Incremento de tráfico

### Contexto

Se generó carga realizando múltiples solicitudes al endpoint de reservas.

### Activación

```bash
for /l %i in (1,1,100) do curl http://localhost:8081/bookings
```

### Evidencia

Insertar captura de Grafana mostrando aumento de requests.

### Resolución

Monitorear comportamiento mediante métricas de Prometheus.

### Lección aprendida

El monitoreo permite identificar picos de tráfico y posibles cuellos de botella.

---

# Parte D – Evidencias del Sistema

## 1. Prometheus Targets

Insertar captura de:

```text
http://localhost:9090/targets
```

Mostrando:

* booking-service UP
* tour-package-service UP

---

## 2. Dashboard de Grafana

Insertar captura con los siguientes paneles:

1. Requests por segundo
2. Latencia promedio
3. Latencia p95
4. Estado de servicios
5. Uso de CPU
6. Reservas creadas

---

## 3. Pods en Running

Comando:

```bash
kubectl get pods -A
```

Insertar captura mostrando:

* booking-service Running
* tour-package-service Running
* prometheus Running
* grafana Running

---

## 4. Alerta FIRING

Insertar captura de Grafana Alerting mostrando una alerta activa durante una casuística.

Ejemplo:

* Servicio no disponible.
* Alta latencia.
* Caída de pod.

---

# Conclusiones

* Se implementó una arquitectura basada en microservicios utilizando Spring Boot.
* Se logró la comunicación entre servicios mediante WebClient.
* Se integró observabilidad utilizando Micrometer, Prometheus y Grafana.
* Se utilizaron contenedores Docker para empaquetar los servicios.
* Kubernetes permitió administrar y escalar los microservicios de forma eficiente.
* El monitoreo facilitó la detección temprana de problemas y el análisis del comportamiento del sistema.

---

# Recomendaciones

* Implementar una base de datos real para persistencia.
* Incorporar autenticación mediante JWT.
* Agregar API Gateway para centralizar el acceso.
* Configurar escalamiento automático en Kubernetes.
* Implementar pruebas automatizadas de integración y carga.
