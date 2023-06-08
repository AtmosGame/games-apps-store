plugins {
	java
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
	jacoco
	id("jacoco")
	id("org.sonarqube") version "4.0.0.2929"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.google.guava:guava:31.1-jre")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("org.apache.commons:commons-lang3")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation ("io.micrometer:micrometer-registry-prometheus")


}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	classDirectories.setFrom(files(classDirectories.files.map {
		fileTree(it) { exclude("**/*Application**", "**/dto/**", "**/exceptions/**") }
	}))
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

sonarqube {
	properties {
		property ("sonar.projectKey", "GAMES_APPS_STORE")
		property ("sonar.organization", "atmos-games-apps-store")
		property ("sonar.sourceEncoding", "UTF-8")
		property ("sonar.branch.name", "main")
		property ("sonar.coverage.exclusions", "**/*Application**,**/dto/**,**/exceptions/**")
	}
}


buildscript {
	repositories {
		maven {
			url = uri("https://plugins.gradle.org/m2/")
		}
	}
	dependencies {
		classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.5.0.2730")
	}
}

apply(plugin = "org.sonarqube")


