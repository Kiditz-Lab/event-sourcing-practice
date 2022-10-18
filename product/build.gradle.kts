plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")

}

val axonVersion: String by project
val mapStructVersion: String by project

dependencies {
    implementation(project(":core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.github.wenhao:jpa-spec:3.2.5")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.axonframework:axon-spring-boot-starter:${axonVersion}")
    implementation("org.mapstruct:mapstruct:${mapStructVersion}")
    kapt("org.mapstruct:mapstruct-processor:${mapStructVersion}")

    runtimeOnly("com.h2database:h2")
}