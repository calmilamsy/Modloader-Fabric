# Modloader-Fabric
Modloader but without base edits.

It loads mods, but not fabric mods.

### TO MAKE MODLOADER MODS:

- Download the [example project](https://github.com/calmilamsy/BIN-fabric-example-mod).

- Download the latest modloader-dev jar from [releases](https://github.com/calmilamsy/modloader-fabric/releases).

- Put the jar you just downloaded into the `lib` folder inside your project.

- **IMPORTANT**: Edit your mod properties inside the `gradle.properties` file in your project root. You MUST set a new archives_base_name.

- Import the project as a Gradle project in your favourite (Intellij IDEA is recommended for mixin support).

- Create a new `mod_<modname>` class inside a package in client src (no server support atm) it does not need to (and should not) be put in the root package.   

- Profit! Make a ModLoader mod just like you would.

- To build on Intellij IDEA, open the Gradle tab on the right go to `<project name> > Tasks` and run the `jar` task. The resulting jars will be in `client/build/libs`.

Notes:
- The dev jar only works inside the dev environment, and the normal jar only works in MMC.
- There are some name changes to various fields and methods to make naming more consistent with the Java naming scheme.

### USING MODLOADER AS A USER:

- Add [multimc-fabric-b1.7.3.zip](https://github.com/js6pak/fabric-loader/releases) as an instance to [MultiMC](https://multimc.org).

- Download the modloader-x.x.x.jar file (NOT dev or sources!) and put it into your mods folder.

- Profit!

Note: You can use old ModLoader/AudioMod/PlayerAPI mods just fine provided they do not edit base classes.