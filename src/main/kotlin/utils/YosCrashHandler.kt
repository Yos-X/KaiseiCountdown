package utils

import androidx.compose.runtime.MutableState


class YosCrashHandler private constructor(errorDialog: MutableState<Boolean>) : Thread.UncaughtExceptionHandler {
    private val mutableErrorDialog = errorDialog

    // 单例模式
    companion object {
        private var instance: YosCrashHandler? = null
        fun getInstance(errorDialog: MutableState<Boolean>): YosCrashHandler? {
            if (instance == null) {
                synchronized(YosCrashHandler::class) {
                    instance = YosCrashHandler(errorDialog)
                }
            }
            return instance
        }
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        handleException(e)
    }

    private fun handleException(e: Throwable) {
        mutableErrorDialog.value = true
    }
}