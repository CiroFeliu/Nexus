---
title: "Tres agentes en la misma app móvil, cada uno con su emulador"
date: 2026-11-17
summary: "Empecé repartiendo mis emuladores entre agentes con un TXT en el escritorio y unas órdenes en el AGENTS.md. Funcionó un tiempo, y cuento por qué rompía y cómo lo reparte ahora ShogunAi."
lang: es
translationKey: shogunai-mobile
tags: [shogunai, agentes, android, ios]
---

La primera vez que se me pisaron dos agentes en un dispositivo fue en el trabajo. Estaba validando una tarea al mismo tiempo que reproducía un bug, y uno de los agentes empezó a quejarse de que el estado de la aplicación estaba cambiando. Lo vi rápido, porque con cuatro o cinco sesiones en paralelo enseguida vi que era otra la que le estaba tocando la app, y pensé lo obvio, que tenía un problema y que tenía que buscar una solución.

La solución fue muy de ir por casa: un TXT en el escritorio para que los agentes cogieran turno sobre mis dispositivos físicos y mis emuladores, y unas órdenes en el `AGENTS.md` para que, cada vez que un agente tuviera que utilizar un dispositivo, cogiera turno.

## Lo intenté con una instrucción

En el [primer post](https://cirofeliu.es/blog/agentes-en-paralelo-sin-chocar/) contaba el caso de un backend, y ahí una línea en el `AGENTS.md` basta para que cada agente use sus puertos. Algún compañero me ha comentado que entonces todo esto se resuelve con una instrucción, y lo del TXT era justo eso.

Durante un tiempo estuvo funcionando, pero cuando las sesiones se dejaban apuntado un dispositivo que ya no usaban, o cosas por el estilo, rompía. No era determinista, como cualquier instrucción que le das a un agente. A parte de esto, los emuladores y los simuladores los tenía que ir creando y eliminando yo a mano, y llegué a tener cinco o seis a la vez.

No era cuestión de que estuviera perdiendo mucho tiempo en el momento, sino que identifiqué que eso escalaba mal, que iba a tener un cuello de botella al tener que servir y proveer yo a los agentes. Necesitaba algo que funcionara por comandos y, como ya tenía un proyecto que gestionaba worktrees, pensé en relacionarlos.

## Por qué móvil

Porque soy desarrollador mobile, y para un martillo todos son clavos. Lo que hace distinto al móvil es sobre todo el tema de los emuladores y las pruebas en dispositivo: un agente no puede dar por buena una pantalla sin instalar la app y mirarla, y todos instalan la misma app en lo que encuentren encendido.

## Cómo lo reparte ShogunAi

El repositorio declara una vez lo que necesita un worktree, en el `.shogunai.json`. Este es el de la app de la demo, hecha con Kotlin Multiplatform para Android e iOS:

```json
{
  "link": ["/local.properties"],
  "providers": {
    "gradle": { "daemonMaxHeap": "3g", "kotlinDaemonMaxHeap": "2g", "idleTimeoutMinutes": 30 },
    "emulator": { "avd": "Medium_Phone_API_35" },
    "ios-simulator": { "device": "iPhone 17", "runtime": "iOS 26.5" },
    "xcode": { "compilationCache": true }
  }
}
```

A partir de ahí, cada agente arranca los dispositivos de su worktree con dos comandos:

```bash
shogun emulator start
shogun simulator start
```

El primero le da un emulador de Android propio y el segundo crea un simulador de iOS con el nombre del worktree. Lo que instala y lo que captura un agente va solo a los suyos, y eso ya lo decide un comando, que hace siempre lo mismo.

Cuántos caben lo decide la máquina y no el repositorio. Hay un límite de emuladores, de simuladores y de daemons de Gradle por máquina, y cuando un agente pide un dispositivo y ya no caben más, espera su turno en una cola hasta que otro termina. Lo he implementado así porque me parece bien que espere. Yo he ido ajustando los parámetros hasta que en el ordenador del trabajo vivo prácticamente al 100 % de la CPU, ahora mismo con unos 6 emuladores de Android, 2 simuladores de iOS y unos 8 daemons, a ojo. El cuello de botella ahora es el hierro, la potencia, pero no es algo súper limitante, porque en el día a día haces más cosas que no solo desarrollar.

Y al quitar un worktree se apaga lo suyo: su emulador, su simulador y sus daemons de Gradle ociosos.

## Tres agentes, una app

![Tres agentes de Claude Code trabajando en la misma app móvil: sin ShogunAi se pisan en el mismo emulador y el mismo simulador, y con ShogunAi cada uno recibe los suyos y el tercero espera turno](demo.mp4)

He hecho la prueba con tres sesiones de Claude Code, cada una con una tarea de la misma app: un buscador, los favoritos y una pantalla de detalle. Cada tarea se da por hecha cuando pasan sus tests y hay una captura de Android y otra de iOS que enseñan el cambio.

Sin ShogunAi, con un emulador y un simulador encendidos, los tres instalaron la misma app en los mismos dispositivos y se la fueron sobrescribiendo. Lo curioso es que se dan cuenta, uno de ellos lo escribió tal cual: «The emulator is showing another agent's build. I'll reinstall mine and take the screenshot right away». Otro tocó la pantalla y se encontró abierta la pantalla de detalle de otro agente. Los que acabaron lo hicieron en unos 4 minutos y con las capturas bien, pero a base de reinstalar y capturar corriendo antes de que otro les pisara, y sin nada que les asegure que lo que están comprobando es lo suyo. En la tanda del vídeo, uno ni siquiera llegó a instalar: vio que otro agente estaba usando el emulador y se paró a preguntarme qué hacía.

Con ShogunAi, en mi máquina caben dos emuladores y dos simuladores. Dos agentes cogieron los suyos y el tercero se encontró el límite, se puso en la cola en segundo plano y siguió con el código y los tests mientras esperaba. Cuando terminó uno de los otros y caducó su reserva, recibió sus dispositivos y acabó. Entre los tres lanzaron 36 órdenes contra Android y todas fueron al emulador de su worktree. Seis capturas, ninguna pisada.

Lo que no sale gratis es el tiempo: con ShogunAi cada agente arranca su propio emulador, y en mi máquina eso han sido entre 4 y 13 minutos por tarea, según la tanda, frente a los 4 de compartirlo todo. Y el tercero esperó unos 20 minutos en la cola, que es lo que tarda en caducar la reserva de un agente que ya ha terminado. Con dos emuladores arrancando a la vez el equipo va justo, y con cuatro directamente no pudo.

## Cuándo no te hace falta

Lo estaba desarrollando para mí, porque a mí sí que me aportaba utilidad, así que no sé a cuánta gente le pasa lo mismo. Si lo tuyo es hacer cambios en varios repositorios al mismo tiempo, supongo que lo puedes cubrir con una instrucción. Cuando haces muchos cambios en paralelo sobre un solo proyecto es donde le veo más potencial a la herramienta.

Si volviera a empezar el proyecto ahora, no perdería tanto tiempo en la parte de los worktrees como perdí al principio, hasta centrarme en el provisionamiento.

## Cómo probarlo

La app de la demo está en [shogunai-demo-mobile](https://github.com/LuxionServer/shogunai-demo-mobile), con sus tres tareas y la rama `without-shogunai` para ver la diferencia, y la instalación de ShogunAi está en el [repositorio](https://github.com/LuxionServer/ShogunAi). Si trabajas con Flutter también hay provider.

Publico esto para dejar de desarrollar en la oscuridad. Si eres desarrollador móvil y tienes los mismos problemas, prueba la herramienta y dame feedback.
