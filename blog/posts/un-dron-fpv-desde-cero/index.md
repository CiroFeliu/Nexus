---
title: "Un dron FPV desde cero siendo de software"
date: 2026-11-11
summary: "En 2023 monté un dron FPV pieza a pieza. Dos meses, entre 2.000 y 3.000 euros, muchos choques y una mano izquierda destrozada después, esto es lo que aprendí de un mundo en el que no hay deshacer."
lang: es
translationKey: fpv-drone-from-scratch
tags: [hardware, dron, fpv]
---

En 2023 decidí montar un dron FPV desde cero. Compré cada pieza por separado y lo ensamblé yo, con la cámara digital de DJI, la O3, y sus gafas, las Goggles 2. Tardé unos dos meses en verlo volar y me gasté entre 2.000 y 3.000 euros. Desde entonces ha volado mucho, se ha estrellado muchas veces, lo he reparado y ha vuelto a volar. Ahora mismo está en tierra esperando componentes, porque la guerra de Ucrania se está llevando buena parte de las piezas de FPV del mercado.

Llevo años haciendo software, y en software casi todo se puede deshacer. Con el dron eso se acabó.

## Montarlo pieza a pieza

Esto es lo que compré, según el ticket:

- Chasis: TBS Source One V5 de 5 pulgadas.
- Controladora y ESC: stack SpeedyBee F405 V3, con un ESC 4 en 1 de 50 A (BLHeli_S, de 3 a 6S), en formato 30x30.
- Motores: cuatro T-Motor Velox Veloce V2207.5 V2 de 1950 KV.
- Hélices: HQProp DP 5x4.3x3, de tres palas.
- Vídeo: unidad aérea DJI O3 y gafas DJI Goggles 2.
- Radio: emisora RadioMaster Boxer con ExpressLRS a 2,4 GHz y receptor RadioMaster RP1.
- Carga y seguridad: cargador ToolKitRC M6D, un smoke stopper XT60 y correas Lumenier para la batería.

Las baterías, de 6S y de varias marcas, porque he ido comprando diferentes. Elegí las piezas muy a ojo.

Eso es solo el pedido inicial. Luego quemé piezas y rompí motores, y todo eso entra en los 2.000 o 3.000 euros.

El GPS tampoco está en el ticket y no recuerdo el modelo; era uno con nombre de dinosaurio.

Empecé con la idea de escribir mi propio firmware. Viniendo del software parecía lo natural: si sé programar, puedo programar la controladora. Partí de un código de Arduino del proyecto de fin de carrera de mi padre. Llegué a leer los acelerómetros y a mover los motores a través de los ESC, pero en algún momento asumí que así no iba a volar nunca, y me pasé a Betaflight.

## Lo que el software no te enseña

**No hay deshacer.** Un error de software se arregla con otro commit. Un error en el dron es una hélice rota, un brazo partido o un motor quemado, y el arreglo empieza por esperar a que llegue la pieza.

**El ruido es real.** Aislar la antena GPS de las interferencias del resto del dron me costó más que cualquier otra cosa del montaje. Cada vez que aceleraba, el GPS perdía la señal. Nunca lo tuve claro al cien por cien, pero mi sospecha eran los picos de corriente de los cables de los motores. La solución fue un apaño de manual: forré la antena con cinta adhesiva de cobre y la soldé a la tierra del circuito, a modo de jaula de Faraday casera.

**Pilotar se aprende a base de horas.** El dron solo tenía modo acro: nada lo nivela por ti, si sueltas los mandos sigue en la inclinación en la que estaba. No calculé las horas de simulador que iba a necesitar antes de poder volarlo sin estrellarlo. Según Steam, 7 horas en TRYP FPV y 5 en Liftoff. Doce horas que se quedaron cortas, porque aun así me estrellé un montón.

## El día que casi pierdo un dedo

Me destrocé la mano izquierda y casi pierdo el índice.

Era domingo, la segunda vez que tenía el dron montado y funcionando, y tenía prisa. Conectar, desconectar, poner hélices, quitarlas... Y en una de esas hice justo lo que Betaflight te avisa que no hagas: tocar nada con las hélices puestas. Con motores que dan hasta 873 vatios cada uno, el dron me destrozó la mano.

Salimos corriendo a urgencias, yo todavía en ropa de estar por casa, y entré directo a quirófano. Más de 20 puntos en el índice, cicatrices en el resto de la mano y dos o tres meses trabajando a base de dictarle al ordenador. Secuelas, ninguna: solo un dedo algo más aerodinámico.

¿Lo que cambió? Ahora no me dejan entrar el dron en casa con las hélices puestas.

## Por qué lo cuento ahora

Hoy trabajo con agentes de IA que escriben buena parte de mi código, y cuanto más rápido va la parte de software, más se nota la que no lo es. Un agente puede escribir una configuración de Betaflight, pero no puede aislar una antena, ni notar cómo vibra un motor, ni aprender a volar en acro por ti.

Pero hay algo más que me llevo del dron. Aquel domingo no me faltaban conocimientos: el aviso estaba en la pantalla. Me faltó pararme a leerlo. Tenía prisa, todo funcionaba, y el exceso de confianza hizo el resto.

Con los agentes siento algo parecido. Nunca habíamos tenido tanta velocidad ni tanto poder para automatizar, y es muy fácil dejarse llevar: lanzar procesos, encadenar tareas y darle a ejecutar sin pensar bien qué estamos automatizando, cómo ni con qué consecuencias. Un error en un agente no te deja 20 puntos en el dedo, pero puede romper cosas que no se arreglan con otro commit.

Ese poder trae una responsabilidad. A veces lo más productivo es lo contrario de correr: quitar las hélices, leer el aviso y pensar un momento antes de conectar.
