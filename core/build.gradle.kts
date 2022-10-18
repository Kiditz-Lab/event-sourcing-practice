plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

val axonVersion: String by project
val mapStructVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.axonframework:axon-spring-boot-starter:${axonVersion}")

}