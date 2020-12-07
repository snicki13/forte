import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val exposed_version: String = "0.25.1"

plugins {
    kotlin("jvm") version "1.3.72"
}

group = "de.snick-it.forte"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "exposed"
        url = uri("https://dl.bintray.com/kotlin/exposed")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("de.jensd:fontawesomefx:8.9")
    implementation("org.controlsfx:controlsfx:8.40.10")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.xerial:sqlite-jdbc:3.32.3.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
    resources.srcDir("src/main/resources")
}
