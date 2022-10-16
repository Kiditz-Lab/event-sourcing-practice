plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}