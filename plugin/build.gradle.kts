dependencies {
    api(project(":api"))
    api(project(":v1_15"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("io.netty:netty-all:4.1.89.Final")
}

tasks {
    shadowJar {
        archiveBaseName.set("PacketAPI")
    }
}
