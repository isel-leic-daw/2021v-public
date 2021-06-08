import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "pt.isel.daw"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	// Example on how to replace Tomcat with Jetty, for the Servlet-based server
	implementation("org.springframework.boot:spring-boot-starter-web") {
		this.exclude("org.springframework.boot","spring-boot-starter-tomcat")
	}
	implementation("org.springframework.boot:spring-boot-starter-jetty")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// for QR code generation
	implementation("net.glxn:qrgen:1.4")

	// for JDBI
	implementation("org.jdbi:jdbi3-core:3.18.1")
	implementation("org.jdbi:jdbi3-kotlin:3.18.1")
	implementation("org.postgresql:postgresql:42.2.19")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Copy>("extractUberJar") {
	dependsOn("assemble")
	from(zipTree("$buildDir/libs/${rootProject.name}-$version.jar"))
	into("build/dependency")
}

task<Exec>("dbTestsUp") {
	commandLine("docker-compose", "up", "-d", "db-tests")
}

task<Exec>("dbTestsWait") {
	commandLine("docker", "exec", "db-tests", "/app/bin/wait-for-postgres.sh", "localhost")
	dependsOn("dbTestsUp")
}

task<Exec>("dbTestsDown") {
	commandLine("docker-compose", "down")
}

tasks {
	named<Test>("test") {
		dependsOn("dbTestsWait")
	}
}
