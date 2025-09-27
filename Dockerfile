# ===== Stage 1: Build (Maven + JDK) =====
FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Código fuente
COPY src ./src

# Empaquetado (sin tests para build más rápido)
RUN mvn -q -DskipTests clean package

# ===== Stage 2: Runtime (JRE) =====
FROM eclipse-temurin:21-jre

# Instalar curl para healthcheck (y tini opcional)
USER root
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl tini \
 && rm -rf /var/lib/apt/lists/*

# Usuario no-root
RUN useradd -ms /bin/bash appuser
WORKDIR /app

# Copia el JAR generado en el stage anterior
COPY --from=build /app/target/*.jar /app/app.jar
RUN chown -R appuser:appuser /app

# Variables (puedes sobreescribirlas con .env / compose)
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS=""

# Healthcheck (requiere actuator y endpoint /actuator/health)
HEALTHCHECK --interval=30s --timeout=5s --retries=5 \
  CMD curl -fsS http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

USER appuser
EXPOSE 8080

# Usa tini como init para manejar señales (opcional pero recomendado)
ENTRYPOINT ["/usr/bin/tini","--"]
CMD ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
