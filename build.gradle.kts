plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.github.tofpu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.yaml:snakeyaml:2.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("org.yaml:snakeyaml:2.0", "io.github.tofpu.dynamicconfiguration.libs")
    }

    test {
        useJUnitPlatform()
    }
}