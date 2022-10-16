import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion: String by project
val springCloudVersion: String by project
val pluginVersion: String by project

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")
}


allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


java.sourceCompatibility = JavaVersion.VERSION_17

subprojects {
    group = "com.stafsus.omnichannel"
    version = "1.0-SNAPSHOT"

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "io.spring.dependency-management")


    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }

    dependencies{
        runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:${pluginVersion}")
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
        kotlinOptions.jvmTarget = "17"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}