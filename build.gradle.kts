import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val exposedVersion: String = "0.25.1"

plugins {
    kotlin("jvm") version "1.3.72"
}

group = "de.snick-it.forte"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    //maven {
    //    name = "exposed"
    //    url = uri("https://dl.bintray.com/kotlin/exposed")
    //}
    maven {
        name = "jcenter"
        url = uri("https://jcenter.bintray.com")
    }

}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("no.tornado:tornadofx:1.7.20")
    //implementation("de.jensd:fontawesomefx:8.9")
    implementation("de.jensd:fontawesomefx-commons:11.0")
    implementation("de.jensd:fontawesomefx-materialicons:2.2.0-11")
    implementation("de.jensd:fontawesomefx-materialstackicons:2.1-11")
    //implementation("org.controlsfx:controlsfx:8.40.10")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.xerial:sqlite-jdbc:3.32.3.2")
    implementation("org.postgresql:postgresql:42.2.18")
    implementation("mysql:mysql-connector-java:8.0.22")


}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
    resources.srcDir("src/main/resources")
}

// Include dependent libraries in archive.
var mainClassName = "de.snickit.forte.ForteMain"

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