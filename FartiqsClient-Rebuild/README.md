# Fartiqs Client GUI rebuild (source project)

This project targets Minecraft **1.21.11**, Java 21, Fabric Loader and Fabric API. It provides a new dark neon module screen and a custom title screen with basic JSON-persisted preferences.

## Important status note
The supplied JAR contains compiled classes only, not original Java source. The three feature mods are preserved as nested JARs under `src/main/resources/META-INF/jars/`. Their logic is not rewritten. The original root mixin class files depend on the original `FartiqsConfig` API; this rebuild currently does **not** carry those root mixins forward. Before replacing the supplied JAR, verify the exact feature behavior in-game and port any root mixin-only integrations that are still required. The UI code is a rebuild scaffold, not a claim that every requested advanced visual effect is complete.

## Project tree

```text
FartiqsClient-Rebuild/
├── build.gradle
├── gradle.properties
├── settings.gradle
├── README.md
└── src/main/
    ├── java/com/fartiqs/client/
    │   ├── FartiqsClient.java
    │   ├── FartiqsConfig.java
    │   ├── FartiqsScreen.java
    │   ├── CustomTitleScreen.java
    │   ├── FartiqsSettingsScreen.java
    │   └── mixin/TitleScreenMixin.java
    └── resources/
        ├── fabric.mod.json
        ├── fartiqsclient.mixins.json
        ├── assets/fartiqsclient/icon.png
        └── META-INF/jars/ (three original feature JARs)
```

## Assets
- `assets/fartiqsclient/icon.png`: copied from the provided JAR.
- `META-INF/jars/fartiqs-profit-hud-1.0.10.jar`: original Profit HUD module.
- `META-INF/jars/fartiqs-2x-pay-mod-1.0.0.jar`: original 2x Pay module.
- `META-INF/jars/fartiqs-auction-mod-3.4.0.jar`: original Auction module.
- No TTF, custom PNG module icons, default wordmark PNG, GIF/video backgrounds, or nine-slice texture atlas is included yet. Current screen uses vanilla text and primitive rounded-looking fills; those advanced assets/effects remain to be implemented.

## Build and test checklist
1. Install a Java 21 JDK and a current Gradle installation (or generate a wrapper with `gradle wrapper`).
2. From this folder run `gradle build`.
3. Put the resulting `build/libs/fartiqs-client-2.0.0.jar` in the `mods` folder with Fabric API.
4. Launch a disposable 1.21.11 Fabric profile, verify the title screen, Right Shift menu, module toggles, and each nested feature module.
5. If it fails, inspect `logs/latest.log` and the crash report before using it in a primary profile.

## Current configuration
Config path: `.minecraft/config/fartiqsclient.json`. The current implementation persists module states, icon toggle, background mode, logo options, particle/watermark toggles and related defaults. Some settings are scaffolding and do not yet render all their intended effects.
