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
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.ibm.db2.jcc:db2jcc:db2jcc4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

publishing {
    repositories {
        maven {
            name = "GitHub-Packages-Alfredo-Santoro"
            url = uri("https://maven.pkg.github.com/AlfredoSantoro/ODS")
            credentials {
                username = "AlfredoSantoro"
                password = "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"
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            artifact("$rootDir/dist/sesalab-${version}-ods.jar")
        }
    }
}
