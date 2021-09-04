import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    kotlin("plugin.jpa") version "1.5.20"
    id("maven-publish")
}

group = "com.unisa"
version = properties["application_version"] as String
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.ibm.db2.jcc:db2jcc:db2jcc4")
    implementation("commons-codec:commons-codec:1.11")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.4")
    runtimeOnly("com.ibm.db2.jcc:db2jcc:db2jcc4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("sesalab-${properties["application_version"]}-ods.jar")
    destinationDirectory.set(layout.buildDirectory.dir("$rootDir/dist"))
}

publishing {
    repositories {
        maven {
            name = "GitHub-Packages-Alfredo-Santoro"
            url = uri("https://maven.pkg.github.com/AlfredoSantoro/ODS")
            credentials {
                username = project.properties["repo_username"] as String
                password = project.properties["repo_password"] as String
            }
        }
    }

    publications {
        register<MavenPublication>("sesalab-${properties["application_version"]}-ods.jar") {
            artifact("$rootDir/dist/sesalab-${properties["application_version"]}-ods.jar")
        }
    }
}
