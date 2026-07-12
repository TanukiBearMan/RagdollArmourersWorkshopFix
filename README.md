# Ragdoll Armourers Workshop Fix

Client-side NeoForge 1.21.1 compatibility fix for Armourer's Workshop and Sable Player Ragdoll.

## What It Fixes

When a player wearing an Armourer's Workshop outfit enters a Sable Player Ragdoll state, Sable renders vanilla player layers once for every ragdoll body part. Armourer's Workshop adds the full wardrobe renderer as one of those layers, so the outfit can be drawn repeatedly and turn the ragdoll model into a duplicated visual mess.

This mod suppresses Armourer's Workshop skin parts that do not belong to the currently rendered Sable ragdoll body part. The outfit stays visually attached to the matching ragdoll limbs instead of being rendered as a full outfit on every part.

## Target

- Minecraft 1.21.1
- NeoForge 21.x
- Armourer's Workshop 3.4.0-beta.2
- Sable Player Ragdoll 0.7.2

## Build

```powershell
.\gradlew.bat clean build
```

The built jar is written to:

```text
build/libs/ragdollawfix-1.1.jar
```
