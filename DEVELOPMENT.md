# DEVELOPMENT.md

Guía rápida para levantar el entorno de desarrollo con **Docker Compose** usando `setup-docker.sh`.

---

## Steps

1. **Fix line endings** *(Windows/WSL only)*

   ```bash
   find . -name "*.sh" -type f -exec sed -i 's/\r$//' {} \;
   ```

2. **Set permissions** *(una sola vez)*

   ```bash
   chmod +x scripts/setup-docker.sh
   ```

3. **Generate and start environment**

   ```bash
   ./scripts/setup-docker.sh
   ```

4. **Verify services are running**

   ```bash
   docker compose ps
   ```

5. **Check logs** *(app only)*

   ```bash
   docker compose logs -f app
   ```

6. **Access services**

   * **API** → [http://localhost:8080](http://localhost:8080)
   * **pgAdmin** → [http://localhost:5050](http://localhost:5050)

      * Email: `admin@example.com`
      * Password: `admin`
      * Host: `db`
      * Port: `5432`
      * Database: `eccomerce`

---

✅ Con esto tienes el entorno de desarrollo funcionando con un solo comando (`./scripts/setup-docker.sh`).

---

## ¿Qué sucede al ejecutar cada comando?

* `find . -name "*.sh" -type f -exec sed -i 's/
  $//' {} \;`

   * **Qué hace:** Normaliza finales de línea (CRLF → LF) en todos los scripts `*.sh`.
   * **Por qué importa:** Evita errores tipo `^M` o `bad interpreter` en WSL/Linux.

* `chmod +x scripts/*.sh`

   * **Qué hace:** Da permisos de ejecución a tus scripts.
   * **Efecto:** Podrás correr `./scripts/...` sin `bash` delante.

* `./scripts/setup-docker.sh`

   * **Qué hace:** Verifica Docker/Compose, **construye** imágenes (`docker compose build`) y **levanta** los servicios (`docker compose up -d`).
   * **Resultado esperado:** Contenedores `app`, `db` (y `pgadmin` si está expuesto) arriba. Muestra `docker compose ps` y la URL de la API.

* `./scripts/update.sh`

   * **Qué hace:** Se posiciona en la raíz del repo, **guarda cambios locales** con `git stash` si hay, ejecuta `git pull --rebase origin main`, intenta **restaurar el stash**, y luego llama a `./scripts/setup-docker.sh`.
   * **Resultado esperado:** Código actualizado desde `main`, stack reconstruido/levantado con los últimos cambios. Si hay conflictos al aplicar el stash, te lo avisa para que resuelvas.

* `docker compose build`

   * **Qué hace:** Reconstruye imágenes según tu `Dockerfile`/`docker-compose.yml`.
   * **Cuándo usarlo:** Cuando cambias **Dockerfile**, **pom.xml** (dependencias) o variables de build.

* `docker compose up -d`

   * **Qué hace:** Levanta (o recrea) los contenedores en segundo plano.
   * **Tip:** Si no cambiaste imágenes, este comando es rápido; si cambiaste, usa primero `build` o `setup-docker.sh`.

* `docker compose logs -f app`

   * **Qué hace:** Sigue los logs en vivo del servicio `app`.
   * **Útil para:** Ver arranque de Spring Boot, errores de conexión a DB, etc.

* `docker compose down -v`

   * **Qué hace:** Detiene y elimina contenedores **y volúmenes** (⚠️ borra datos de Postgres).
   * **Cuándo usarlo:** Resets completos del entorno o cuando cambiaste el esquema y quieres empezar de cero.

---

## Atajos habituales

* **Actualizar y desplegar de una:**

  ```bash
  ./scripts/update.sh
  ```
* **Solo ver logs de la app:**

  ```bash
  ./scripts/logs.sh
  ```
* **Apagar todo y limpiar (incluye DB):**

  ```bash
  ./scripts/down.sh
  ```

---

## Notas rápidas

* Si cambias `pom.xml` o `Dockerfile`, habrá **rebuild** (más lento) la siguiente vez.
* Si solo cambias código Java en desarrollo (hot reload), no hace falta rebuild: DevTools se encarga.
* Para acceder a pgAdmin desde el navegador, asegúrate de tener expuesto `pgadmin: ports: - "${PGADMIN_PORT}:80"` y luego ve a `http://localhost:${PGADMIN_PORT}`.
