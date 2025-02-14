package plugin.utils

import org.gradle.api.Project
import java.io.File

/**
 * description:
 * author chaojiong.zhang
 * data: 2021/11/2
 * copyright TCL+
 */


//判断是否子 project 的
fun hasAndroidPlugin(curProject: Project): Boolean {
    return curProject.plugins.hasPlugin("com.android.library")
}

//判断是否子 project 的
fun hasAppPlugin(curProject: Project): Boolean {
    return curProject.plugins.hasPlugin("com.android.application")
}

//判断是否java project 的
fun hasJavaPlugin(curProject: Project): Boolean {
    return curProject.plugins.hasPlugin("java-library")
}


fun isRunAssembleTask(curProject: Project): Boolean {
    return curProject.projectDir.absolutePath.equals(curProject.gradle.startParameter.currentDir.absolutePath)
}


fun isEnable(curProject: Project): Boolean {
    val enableFile =
        File(curProject.rootProject.rootDir.absolutePath + File.separator + ".gradle" + File.separator + "rocketXEnable")
    return enableFile.exists()
}