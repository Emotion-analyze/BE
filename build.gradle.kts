

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "emotion"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("io.jsonwebtoken:jjwt-api:0.10.7")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.10.7")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.10.7")

    implementation("org.springframework.data:spring-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")

    implementation("org.hibernate.validator:hibernate-validator:7.0.2.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    implementation ("com.amazonaws:aws-java-sdk-s3:1.12.281")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")

    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("net.nurigo:sdk:4.2.7")
    implementation("io.github.flashvayne:chatgpt-spring-boot-starter:1.0.4")
    implementation ("org.encog:encog-core:3.4")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
