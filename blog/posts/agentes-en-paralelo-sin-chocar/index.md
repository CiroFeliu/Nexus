---
title: "Tres agentes en el mismo proyecto sin que se pisen"
date: 2026-10-26
summary: "Un worktree aísla el código de un agente, pero no su entorno. He hecho ShogunAi para que cada worktree tenga sus puertos, sus servicios y sus dispositivos, y poder tener varios agentes trabajando a la vez en la misma máquina."
lang: es
translationKey: shogunai-intro
tags: [shogunai, agentes, worktrees]
---

Llevo meses trabajando con varios agentes de IA a la vez. En el trabajo tengo entre 20 y 30 worktrees en diversos estados, uno por tarea, y en mis proyectos personales uso Claude y, cuando me como la cuota, Antigravity. Lanzar tres agentes en paralelo, cada uno con su tarea, es lo normal para mí, y las herramientas lo ponen fácil: Claude Code, Codex o Cursor crean un worktree de Git por agente y cada uno trabaja en su rama sin tocar el código de los demás.

El problema llega cuando los tres intentan arrancar el proyecto.

## El worktree aísla el código, pero no el entorno

Un worktree es una copia de trabajo del repositorio en otra carpeta, así que el código queda resuelto, pero un agente necesita bastante más que código para trabajar:

- Los ficheros locales que no están en Git y sin los que la build no arranca, como un `.env` o un `local.properties`.
- Sus propios puertos. Si el proyecto levanta la API en el 8080 y la base de datos en el 5432, el segundo agente se encuentra con un «port is already allocated».
- Sus propios servicios. Si el `compose.yaml` fija el nombre del proyecto, como hacen muchos, dos `docker compose up` desde dos worktrees comparten contenedores, y el segundo recrea los del primero. Lo peor es que no da ningún error: el primer agente sigue probando en `localhost:8080`, pero lo que responde ya es el código del otro.
- Sus propios dispositivos. Dos agentes instalando en el mismo emulador o simulador se pisan la app el uno al otro.
- Daemons y cachés con límites. Cada worktree que compila con Gradle o Xcode deja daemons y cachés que se van comiendo la memoria y el disco.

Un agente no ve nada de esto por sí solo, y tampoco ve a los demás. Los agentes de ahora se apañan bastante bien: miran qué hay levantado y, si ven el 8080 ocupado, se inventan otros puertos o te paran para preguntarte. Funciona, pero cada uno lo improvisa a su manera y en cada sesión, y al acabar te quedan tres stacks levantados en puertos que no has elegido tú. Podrías ponerlo en el `AGENTS.md`, y para los puertos sirve, pero una instrucción no reparte emuladores, no acota la memoria de los daemons ni sabe qué ha cogido el agente de al lado. Así que o el agente se pasa la sesión peleándose con el entorno, o te toca a ti hacer de guardia de tráfico, que es lo que me pasaba a mí.

## Qué hace ShogunAi

ShogunAi es la capa de entorno para agentes que trabajan en paralelo en una máquina. No ejecuta agentes ni sustituye a tu herramienta, lo que hace es preparar y supervisar los worktrees donde trabajan Claude, Codex, Cursor o el que uses.

El repositorio declara una vez lo que necesita un worktree, en un `.shogunai.json` que va versionado con el código:

```json
{
  "copy": [".env"],
  "providers": {
    "docker-compose": { "portVariables": ["DB_PORT", "API_PORT"] }
  }
}
```

Y el `compose.yaml` publica los puertos a través de esas variables:

```yaml
services:
  db:
    image: postgres:17
    ports: ["${DB_PORT:-5432}:5432"]
  api:
    build: .
    ports: ["${API_PORT:-8080}:8080"]
```

A partir de ahí, cada worktree que se crea tiene su copia del `.env`, su propio proyecto de Compose (`COMPOSE_PROJECT_NAME`) y sus propios puertos libres, reservados para él. Los tres agentes hacen su `docker compose up` y ninguno se pisa con los demás.

## Tres agentes, un proyecto

![Tres agentes de Claude Code trabajando en el mismo backend, cada uno con su base de datos y sus puertos, y la torre de control de ShogunAi mostrando su estado](demo.mp4)

En el vídeo hay tres sesiones de Claude Code trabajando en tres tareas del mismo backend, primero sin ShogunAi y luego con él. Sin ShogunAi, cada agente mira a mano qué hay levantado, uno se para a preguntarme qué hacer y otro se inventa sus puertos, y al acabar quedan tres stacks en el 8080, el 18080 y el 8180. Con ShogunAi, cada agente recibe sus puertos al arrancar, levanta su base de datos y su API sin buscar ni preguntar, y al quitar el worktree se baja su Compose. En la app de escritorio, que es la torre de control, se ve de un vistazo en qué worktree está cada agente, cuál está esperando a que le respondas y cuál ha terminado.

## Se adapta a tu herramienta, y no al revés

ShogunAi tiene dos partes. Por un lado está el comando `shogun` con sus hooks, que es donde pasa el trabajo: con tres hooks en `.claude/settings.json`, `claude --worktree` crea sus worktrees a través de ShogunAi y cada sesión arranca con sus variables ya cargadas, y hay recetas para Herdr, Cursor, Codex, direnv o un script cualquiera. Por otro lado está la app de escritorio, para verlo todo de un vistazo en todos tus proyectos, incluidos los worktrees que hayan creado otras herramientas.

Docker Compose es solo un ejemplo. El proyecto nació alrededor de Android, así que hay proveedores para los stacks donde preparar un worktree cuesta más: un emulador de Android o un simulador de iOS por worktree, daemons de Gradle con la memoria acotada, builds de Maven y cachés de Xcode compartidas entre worktrees. Si un repositorio no los activa, no los ve.

## Cómo empezar

En macOS:

```bash
brew tap LuxionServer/shogunai
brew trust --tap LuxionServer/shogunai
brew install --cask shogunai
```

En Windows con `winget install LuxionServer.ShogunAi` o con Scoop, y en Linux con el `.deb` de la [última release](https://github.com/LuxionServer/ShogunAi/releases/latest).

Abres la app, añades tu repositorio y, si tiene un `compose.yaml` o una build de Gradle, te sugiere los proveedores que encajan. Todo lo demás está en el [repositorio](https://github.com/LuxionServer/ShogunAi), que es código abierto, y si quieres reproducir el vídeo tal cual, el proyecto de la demo con sus tres tareas está en [shogunai-demo](https://github.com/LuxionServer/shogunai-demo).

Si tú también lanzas varios agentes a la vez y algo de esto te suena, me interesa mucho saber qué es lo que te choca en tu stack. [Abre un issue](https://github.com/LuxionServer/ShogunAi/issues) o escríbeme, sin problema.
