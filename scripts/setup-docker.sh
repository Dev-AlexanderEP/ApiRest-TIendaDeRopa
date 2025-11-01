#!/usr/bin/env bash
set -euo pipefail

echo "🔧 Spring Boot + Docker Setup"
echo "=================================================="



# 2) Verifica Docker y Compose
if ! command -v docker >/dev/null 2>&1; then
  echo "[ERROR] Docker no está instalado o no está en PATH."
  exit 1
fi
if ! docker compose version >/dev/null 2>&1; then
  echo "[ERROR] docker compose V2 no está instalado (prueba: docker --version y docker compose version)."
  exit 1
fi

# 3) Build + up
echo "[INFO] Building images..."
docker compose build

echo "[INFO] Starting stack..."
docker compose up -d

echo "[INFO] Services:"
docker compose ps

APP_PORT="${APP_PORT:-8080}"
echo "✅ Listo. API: http://localhost:${APP_PORT}"
