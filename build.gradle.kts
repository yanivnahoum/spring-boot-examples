plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.6"
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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("com.oracle.database.jdbc:ojdbc11")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    val jvmArgs = listOf("--enable-preview")
    withType<JavaCompile>().configureEach {
        with(options) {
            release = 21
            compilerArgs.addAll(jvmArgs + "-Xlint:all,-processing,-auxiliaryclass")
        }
    }

    test {
        jvmArgs(jvmArgs)
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
        }
    }

    withType<JavaExec>().configureEach {
        jvmArgs(jvmArgs)
    }
}
