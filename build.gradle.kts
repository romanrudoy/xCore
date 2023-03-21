plugins {
    id("java")
}

group = "com.kervand.core"
version = "0.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven ("https://repo.kryptonmc.org/releases")
    maven("https://jitpack.io")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    compileOnly ("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly ("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")

    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")

    testCompileOnly ("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.24")

    compileOnly("com.github.romanrudoy:xAPI:v1.0.1")
    compileOnly("co.aikar:acf-paper:0.5.1-SNAPSHOT")

    compileOnly ("me.neznamy:tab-api:3.2.4")
    compileOnly ("me.clip:placeholderapi:2.11.2")

    compileOnly("com.github.Brikster:Chatty:v2.19.12")
    compileOnly("org.jetbrains:annotations:16.0.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}