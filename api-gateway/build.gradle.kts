val springCloudVersion: String by project
plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.82.Final:osx-aarch_64")
}
