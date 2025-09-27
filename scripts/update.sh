#!/usr/bin/env bash
set -euo pipefail

echo "🔄 Update & Deploy (git pull + Docker)"
echo "======================================"

# 0) Ir a la raíz del repo (este script está en scripts/)
cd "$(dirname "$0")/.." || { echo "[ERROR] No se pudo ubicar la raíz del repo"; exit 1; }

# 1) Verificaciones básicas
if ! command -v git >/dev/null 2>&1; then
  echo "[ERROR] git no está instalado o no está en PATH."
  exit 1
fi
if ! command -v docker >/dev/null 2>&1; then
  echo "[ERROR] Docker no está instalado o no está en PATH."
  exit 1
fi
if ! docker compose version >/dev/null 2>&1; then
  echo "[ERROR] docker compose V2 no está instalado (prueba: docker --version y docker compose version)."
  exit 1
fi

# 2) Mostrar rama y remoto
current_branch="$(git rev-parse --abbrev-ref HEAD)"
echo "[INFO] Rama actual: ${current_branch}"
echo "[INFO] Remote origin: $(git remote get-url origin)"

# 3) Guardar cambios locales si existen
if ! git diff --quiet || ! git diff --cached --quiet; then
  echo "[WARN] Cambios locales detectados. Creando stash temporal..."
  git stash push -u -m "update.sh auto-stash $(date +%F-%H%M%S)"
  STASH_CREATED=1
else
  STASH_CREATED=0
fi

# 4) Traer últimos cambios de main (rebase para un historial limpio)
echo "[INFO] Actualizando desde origin/main..."
git fetch origin --tags
git checkout main
git pull --rebase origin main

# 5) Restaurar stash si había cambios
if [ "${STASH_CREATED}" -eq 1 ]; then
  echo "[INFO] Restaurando cambios locales del stash..."
  # Intentar aplicar; si falla, dejar el stash y avisar
  if git stash apply; then
    # si aplica ok, quitarlo
    git stash drop || true
  else
    echo "[WARN] No se pudo aplicar automáticamente el stash. Revisa con: git stash list / git stash apply"
  fi
fi

# 6) Ejecutar el setup de Docker (build + up)
echo "[INFO] Ejecutando scripts/setup-docker.sh ..."
chmod +x scripts/setup-docker.sh
scripts/setup-docker.sh

echo "✅ Update & Deploy completado."
