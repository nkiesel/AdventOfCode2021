import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
}

group = "mst.internal"
version = "2021"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-assertions-core:4.6.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
    sourceSets.all {
        languageSettings.optIn("kotlin.ExperimentalStdlibApi")
    }
}
