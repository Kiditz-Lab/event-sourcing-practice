rootProject.name = "practice"

pluginManagement {
    val pluginVersion: String by settings
    val springBootVersion: String by settings
    val dependencyManagementVersion: String by settings
    plugins {
        kotlin("jvm") version pluginVersion
        kotlin("kapt") version pluginVersion
        kotlin("plugin.spring") version pluginVersion
        kotlin("plugin.jpa") version pluginVersion

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version dependencyManagementVersion
    }
}

include(":discovery", "api-gateway", ":core",":product", ":order")