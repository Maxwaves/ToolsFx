group = "me.leon.toolsfx"
version = "1.10.1"

plugins {
    application
}

javafx {
    //latest version https://mvnrepository.com/artifact/org.openjfx/javafx-controls
    version = rootProject.extra["jfx_version"] as String
    modules = listOf(
        "javafx.controls",
        "javafx.swing",
        "javafx.web",
//            if you use javafx.fxml,then uncomment it
//            'javafx.fxml'
    )
}
application {
    mainClass.set("me.leon.MainKt")
}

dependencies {
    implementation("no.tornado:tornadofx:${rootProject.extra["tornadofx_version"]}")
    implementation("org.bouncycastle:bcprov-jdk15on:${rootProject.extra["bouncycastle_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${rootProject.extra["kotlin_version"]}")
    implementation("org.glassfish:javax.json:${rootProject.extra["javax_json_version"]}")
    implementation("com.google.zxing:javase:${rootProject.extra["zxing_version"]}")
    api("com.google.code.gson:gson:2.8.9")
    implementation(project(":plugin-lib"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${rootProject.extra["kotlin_version"]}")
    testImplementation("org.apache.commons:commons-compress:1.21")
//    testImplementation("com.github.veithen.cosmos.bootstrap:org.tukaani.xz:0.3")
    testImplementation("com.github.luben:zstd-jni:1.5.2-1")
    testImplementation("org.objectweb.asm:com.springsource.org.objectweb.asm:3.2.0")
    testImplementation("org.tukaani:xz:1.9")
    testImplementation("org.brotli:dec:0.1.2")
}