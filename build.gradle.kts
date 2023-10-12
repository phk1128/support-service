plugins {
    java
    id("org.springframework.boot") version "2.7.16"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.8"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
//	implementation("io.jsonwebtoken:jjwt:0.9.1")
//	compileOnly ("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.0")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // JWT
    compileOnly("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}

//dependencyManagement {
//	imports {
//		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
//	}
//}

tasks.withType<Test> {
    useJUnitPlatform()
}
