plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.freefair.lombok") version "8.1.0"
}

group = "com.att.training"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["snakeyaml.version"] = "2.0"
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<JavaCompile>().configureEach {
        with(options) {
            release = 17
            compilerArgs.add("-Xlint:all,-processing,-auxiliaryclass")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
        }
    }
}
