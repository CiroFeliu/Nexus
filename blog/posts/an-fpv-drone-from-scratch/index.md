---
title: "Building an FPV drone from scratch as a software developer"
date: 2026-11-11
summary: "In 2023 I built an FPV drone piece by piece. Two months, 2,000 to 3,000 euros, plenty of crashes and one wrecked left hand later, this is what I learned from a world with no undo."
lang: en
translationKey: fpv-drone-from-scratch
tags: [hardware, drone, fpv]
---

In 2023 I decided to build an FPV drone from scratch. I bought every part separately and assembled it myself, with DJI's digital camera, the O3, and their goggles, the Goggles 2. It took about two months to see it fly and cost me between 2,000 and 3,000 euros. Since then it has flown a lot, crashed many times, been repaired and flown again. Right now it's grounded, waiting for parts, because the war in Ukraine is taking a large share of the FPV components on the market.

I've been writing software for years, and in software almost everything can be undone. With the drone, that was over.

## Building it piece by piece

This is what I bought, according to the receipt:

- Frame: TBS Source One V5, 5 inch.
- Flight controller and ESC: SpeedyBee F405 V3 stack, with a 50 A 4-in-1 ESC (BLHeli_S, 3 to 6S), 30x30 mounting.
- Motors: four T-Motor Velox Veloce V2207.5 V2, 1950 KV.
- Props: HQProp DP 5x4.3x3, three blades.
- Video: DJI O3 Air Unit and DJI Goggles 2.
- Radio: RadioMaster Boxer transmitter with ExpressLRS 2.4 GHz and a RadioMaster RP1 receiver.
- Charging and safety: ToolKitRC M6D charger, an XT60 smoke stopper and Lumenier battery straps.

The batteries were 6S, from several brands, because I kept buying different ones. I picked the parts pretty much by eye.

That was just the initial order. Later I burnt out parts and broke motors, and all of that is part of the 2,000 to 3,000 euros.

The GPS isn't on the receipt either and I don't remember the model; it was one named after a dinosaur.

I started out planning to write my own firmware. Coming from software it felt natural: if I can code, I can code the flight controller. I started from some Arduino code from my father's final-year university project. I got as far as reading the accelerometers and spinning the motors through the ESCs, but at some point I accepted that it was never going to fly that way, and I switched to Betaflight.

## What software doesn't teach you

**There's no undo.** A software bug gets fixed with another commit. A mistake on the drone is a broken prop, a snapped arm or a burnt motor, and the fix starts with waiting for the part to arrive.

**Noise is real.** Isolating the GPS antenna from the interference of the rest of the drone was harder than anything else in the build. Every time I throttled up, the GPS lost its signal. I was never a hundred percent sure, but my suspicion was the current spikes in the motor wires. The fix was a textbook hack: I wrapped the antenna in adhesive copper tape and soldered it to the circuit's ground, as a homemade Faraday cage.

**Flying is learned in hours.** The drone only had acro mode: nothing levels it for you, and if you let go of the sticks it stays at whatever angle it was. I didn't count on how many simulator hours I'd need before I could fly it without crashing. According to Steam, 7 hours in TRYP FPV and 5 in Liftoff. Twelve hours that fell short, because I still crashed a lot.

## The day I nearly lost a finger

I wrecked my left hand and nearly lost my index finger.

It was a Sunday, the second time I had the drone built and working, and I was in a hurry. Plug in, unplug, props on, props off... And at one point I did exactly what Betaflight warns you not to do: touch anything with the props on. With motors rated at up to 873 watts each, the drone tore my hand apart.

We rushed to the emergency room, me still in the clothes I wear around the house, and I went straight into surgery. More than 20 stitches in my index finger, scars on the rest of my hand and two or three months working by dictating to my computer. No lasting damage: just a slightly more aerodynamic finger.

What changed? Now I'm not allowed to bring the drone into the house with the props on.

## Why I'm telling this now

Today I work with AI agents that write much of my code, and the faster the software side goes, the more the other side stands out. An agent can write a Betaflight config, but it can't isolate an antenna, feel a motor vibrate or learn to fly acro for you.

But there's something else I take from the drone. That Sunday I wasn't short on knowledge: the warning was right there on the screen. What I didn't do was stop and read it. I was in a hurry, everything was working, and overconfidence did the rest.

With agents I feel something similar. We've never had this much speed or this much power to automate, and it's very easy to get carried away: launch processes, chain tasks and hit run without really thinking about what we're automating, how, or with what consequences. A mistake with an agent won't leave you with 20 stitches in your finger, but it can break things that another commit won't fix.

That power comes with responsibility. Sometimes the most productive thing is the opposite of rushing: take the props off, read the warning and think for a moment before you plug in.
