import com.google.protobuf.gradle.id

plugins {
    java
    id("org.springframework.boot") version "3.0.5"
    id("com.google.protobuf") version "0.9.4"
}

val grpcVersion = "1.59.0"
val protobufVersion = "3.25.0"

group = "ru.demmy.grpc"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "com.google.protobuf")

    dependencies {

        implementation("io.grpc:grpc-stub:${grpcVersion}")
        implementation("io.grpc:grpc-protobuf:${grpcVersion}")
        implementation("io.grpc:grpc-netty-shaded:${grpcVersion}")
        implementation("io.grpc:protoc-gen-grpc-java:${grpcVersion}")
        implementation("com.google.protobuf:protobuf-java:${protobufVersion}")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.core:jackson-core:2.15.3")

        protobuf(rootProject.files("proto/"))

        implementation("javax.annotation:javax.annotation-api:1.2")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
    }

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:${protobufVersion}"
        }
        plugins {
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
            }
        }

        generateProtoTasks {
            ofSourceSet("main").forEach {
                it.plugins {
                    id("grpc") { }
                }
            }
        }
    }

}



