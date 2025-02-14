package plugin.utils

import com.android.build.gradle.AppExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import plugin.RocketXPlugin
import plugin.localmaven.AarFlatLocalMaven
import java.io.File

/**
 * description:
 * author chaojiong.zhang
 * data: 2021/11/27
 * copyright TCL+
 */
class InstallApkByAdb(val appProject: Project) {


    fun maybeInstallApkByAdb() {
        if (isRunAssembleTask(appProject)) {
            val android = appProject.extensions.getByType(AppExtension::class.java)
            val installTask =
                appProject.tasks.maybeCreate("rocketxInstallTask",
                    InstallApkTask::class.java)
            installTask.android = android
            android.applicationVariants.forEach {
                getAppAssembleTask(RocketXPlugin.ASSEMBLE + it.flavorName.capitalize() + it.buildType.name.capitalize())?.let { taskProvider ->
                    taskProvider.configure{
                           it.finalizedBy(installTask)
                       }
                    }
            }
        }
    }

    fun getAppAssembleTask(name: String): TaskProvider<Task>? {
        var taskProvider: TaskProvider<Task>? = null
        try {
            taskProvider = appProject.tasks.named(name)
        } catch (ignore: Exception) {
        }
        return taskProvider
    }


    open class InstallApkTask : DefaultTask() {
        lateinit var android:AppExtension
        @TaskAction
        fun installApk() {
          val adb =  android.adbExecutable.absolutePath
            project.exec {
                it.commandLine(adb,
                    "install","-r",FileUtil.getApkLocalPath())
            }
            project.exec {
                it.commandLine(adb,"shell","monkey","-p",
                    android.defaultConfig.applicationId,"-c","android.intent.category.LAUNCHER","1")
            }
        }
    }


}