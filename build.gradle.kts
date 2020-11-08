import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
}

group = "de.snick-it.forte"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("de.jensd:fontawesomefx:8.9")
    implementation("org.controlsfx:controlsfx:8.40.10")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
    resources.srcDir("src/main/resources")
}
