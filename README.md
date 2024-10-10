[![Curseforge](https://img.shields.io/curseforge/dt/1097456?style=for-the-badge&color=6aa84f&logo=curseforge&label=FLUFFY%20FUR)](https://www.curseforge.com/minecraft/mc-mods/fluffy-fur-reborn)
[![Modrinth](https://img.shields.io/modrinth/dt/fluffy-fur?style=for-the-badge&color=6aa84f&logo=modrinth&label=FLUFFY%20FUR)](https://modrinth.com/mod/fluffy-fur)
[![](https://img.shields.io/badge/%20-LICENSE%20GPL--2.0-blue?style=for-the-badge&color=blue&logo=github&logoColor=000000&labelColor=FFFFFF)](https://github.com/MaxBogomol/FluffyFur/blob/master/LICENSE)
[![Discord](https://img.shields.io/discord/1155188824360624148?style=for-the-badge&color=6aa84f&logo=discord&label=DISCORD)](https://discord.gg/cKf55qNugw)

> [!IMPORTANT]
> Status: **Alpha**
>
> #### INSTALATION STABLE
> Duplicate the project, run it in IDE and publish to local maven using
>
> ```java
> // Modrinth Way
> repositories {
>   maven {
>        name = "Modrinth"
>        url = "https://api.modrinth.com/maven"
>    }
> }
>
> dependencies {
>    // your code here...
>    implementation fg.deobf("maven.modrinth:fluffy_fur:${minecraft_version}-${fluffy_fur_version}")
> }
> 
> // Curseforge Way
> repositories {
>    maven { url "https://cursemaven.com"}
> }
>
> dependencies {
>    // your code here...
>    implementation fg.deobf("curse.maven:fluffy_fur-${fluffy_fur_version}:1097456")
> }
> ```
> #### INSTALATION DEV
> _Running:_ `gradlew publishToMavenLocal`
> ```java
>
> repositories {
>    mavenLocal();
> }
>
> dependencies {
>    // your code here...
>    implementation fg.deobf("mod.maxbogomol.fluffy_fur:fluffy_fur:${minecraft_version}-${fluffy_fur_version}")
> }

# About:

"Fluffy Fur" - a mod library by MaxBogomol  
Adds various things for rendering and simplifying development

![](https://cdn.modrinth.com/data/srqzRpcV/images/535438bcbee00c80044ac6536511177e300c375d.png)

# Features:
Shaders support  
Flexible particle and rendering system  
Items particles and animations  
Custom items models  
Screenshake  
Custom panoramas and menu  
Attribute name modifiers  
Music modifiers  
Custom player and items skin system  
...and many other features to simplify development

> [!NOTE]
> # Used by:
> [![Wizard's Reborn](https://img.shields.io/badge/%20-WIZARD'S%20REBORN-5800ff?style=for-the-badge&color=cc762f&logo=github&logoColor=000000&labelColor=FFFFFF)](https://github.com/MaxBogomol/WizardsReborn)
> [![Valoria](https://img.shields.io/badge/%20-VALORIA-5800ff?style=for-the-badge&color=d77787&logo=github&logoColor=000000&labelColor=FFFFFF)](https://github.com/IriDark/Valoria)

if you want help to connect the library to your mod or learn about the development of my mods, you can join our **[Discord Server](https://discord.gg/cKf55qNugw)**