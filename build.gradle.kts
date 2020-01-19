import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.3.0.BUILD-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("plugin.jpa") version "1.3.61"
}

group = "org.server"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    /* Kotlin */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    /* Kotlin Server */
    implementation("io.ktor:ktor-server-netty:1.3.0")

    /* Spring Server */
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-amqp")
//    implementation("org.springframework.boot:spring-boot-starter-artemis")
//    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
//    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
//    implementation("org.springframework.boot:spring-boot-starter-data-redis")
//    implementation("org.springframework.kafka:spring-kafka")

    /* Driver */
    runtimeOnly("org.postgresql:postgresql")

    /* Lombok */
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    /* Testing */
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
//    testImplementation("org.springframework.amqp:spring-rabbit-test")
//    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.bootJar {
    enabled = true
    mainClassName = "org.server.crud.ktor.CrudKtorApplicationKt"
    archiveFileName.set("jar-${archiveFileName.get()}")
}

tasks.build {
    dependsOn("bootKtorJar", "bootSpringJar")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.create<BootJar>("bootKtorJar") {
    group = "build"
    description = "Assembles an executable jar archive containing the main ktor classes and their dependencies."
    mainClassName = "org.server.crud.ktor.CrudKtorApplicationKt"
    archiveFileName.set("ktor-${archiveFileName.get()}")

    dependsOn("compileKotlin", "classes")

    bootInf {
        into("lib") { from(configurations.runtimeClasspath) }
        into("classes") { from("$buildDir/classes/kotlin/main", "$buildDir/resources/main") }
    }
}

tasks.create<BootJar>("bootSpringJar") {
    group = "build"
    description = "Assembles an executable jar archive containing the main spring classes and their dependencies."
    mainClassName = "org.server.crud.spring.CrudSpringApplicationKt"
    archiveFileName.set("spring-${archiveFileName.get()}")

    dependsOn("compileKotlin", "classes")

    bootInf {
        into("lib") { from(configurations.runtimeClasspath) }
        into("classes") { from("$buildDir/classes/kotlin/main", "$buildDir/resources/main") }
    }
}
