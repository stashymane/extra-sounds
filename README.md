# ExtraSounds `v3`

> **[Modrinth](https://modrinth.com/mod/extrasounds)
| [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extrasounds)**


Make your Minecraft more tactile. With sound.

## Version 3

This branch is still a work in progress! Beware of errors, crashes and/or nuclear detonation.

This is a rewrite of the entire mod with Kotlin, but that's not the main focus of the new version.
The actual focus is ensuring as much stability as possible, and supporting past & future versions of Minecraft without
having to release new versions.

### v3 tasks

- [x] Rewrite base code in Kotlin
- [ ] Reimplement all v2 sounds for 1.19
- [ ] Backport sounds to at least 2 older major versions (1.18, 1.17)

## Features

* UI sounds (item clicks, equips)
* Item sounds (bow pulls, redstone repeater clicks, item drops)
* Effect sounds (potions, buffs, debuffs)

## Development

As with v2, you can add item sounds to your own modded items or blocks.
The `sounds.json` format has not changed, however the classes & functions for generating sounds programmatically have -
a full writeup will be done in the wiki once we're near release, but for now you'll have to poke & prod at our source to
figure out how it works. Fortunately, there aren't that many differences.
[Check the wiki](https://github.com/stashymane/extra-sounds/wiki) for how it was done in v2,
and the `mapping` package for how it's done in v3.