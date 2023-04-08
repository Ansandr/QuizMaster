plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "fun.socialcraft"
version = "1.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
}

dependencies {
    compileOnly(
        group = "io.papermc.paper",
        name = "paper-api",
        version = "1.19.4-R0.1-SNAPSHOT"
    )
    implementation  (
        group = "dev.dejvokep",
        name = "boosted-yaml",
        version = "1.3"
    )
    compileOnly(
        group = "com.comphenix.protocol",
        name = "ProtocolLib",
        version = "4.8.0"
    )
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}
