import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar


val ktorVersion: String by project
val exposedVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project

val mainClassName:String by project

plugins {
    application
    kotlin("jvm") version "1.3.72"
}

group = "de.snick-it.forte"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven {
        name = "jcenter"
        url = uri("https://jcenter.bintray.com")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("de.jensd:fontawesomefx-commons:8.15")
    implementation("de.jensd:fontawesomefx-materialicons:2.2.0-5")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.xerial:sqlite-jdbc:3.32.3.2")
    implementation("org.postgresql:postgresql:42.2.18")
    implementation("mysql:mysql-connector-java:8.0.22")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("org.koin:koin-core:$koinVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
    resources.srcDir("src/main/resources")
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-standalone"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = mainClassName
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
