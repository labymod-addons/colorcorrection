import net.labymod.labygradle.common.extension.model.labymod.ReleaseChannel
import net.labymod.labygradle.common.internal.labymod.addon.model.AddonMeta

plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
    id("org.cadixdev.licenser") version ("0.6.1")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "org.example"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "net.labymod.addons.colorcorrection" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
            }
        }
    }

    addonInfo {
        namespace = "colorcorrection"
        displayName = "Color Correction"
        author = "LabyMedia GmbH"
        version = rootProject.version.toString()
        releaseChannel = ReleaseChannel.create("internal_refactor_renderpipeline")
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")
    plugins.apply("org.cadixdev.licenser")

    license {
        header(rootProject.file("gradle/LICENSE-HEADER.txt"))
        newLine.set(true)
    }
}
