package ru.cyclone.cycfinans.data.local.preferences

import android.annotation.SuppressLint
import ru.cyclone.cycnote.BuildConfig
import java.io.File

class PreferencesController(
    tableName: String
) {
    private companion object {
        private val DIVIDER = "\n".toByteArray()
        private const val DIVIDER_STRING = "\n"
    }
    var fileNameList : MutableList<String> = update()

    @SuppressLint("SdCardPath")
    private val storage =  StorageController("/data/user/0/${ BuildConfig.APPLICATION_ID }/files", tableName, DIVIDER_STRING, DIVIDER)

    fun saveLists() {
        storage.file.writeText("")
        fileNameList.forEach { fileData ->
            storage.append(fileData.toByteArray())
        }
    }

    private fun update(): MutableList<String> {
        try {
            val fileList = storage.nameList()
            fileNameList = MutableList(fileList.size) { i ->
                fileList[i]
            }
            return fileNameList
        } catch (_: Exception) {}
        return mutableListOf()
    }

    init {
        update()
    }
}

class StorageController(
    filesDir: String,
    fileName: String,
    private val dividerString: String,
    private val divider: ByteArray
) {
    private val dir = File(filesDir, "config")
    val file = File(dir, fileName)

    init {
        if (!dir.exists()) {
            dir.mkdir()
        }
    }
    fun append(body: ByteArray) {
        file.appendBytes(body + divider)
    }

    fun nameList(): MutableList<String> {
        val s = file.readText().split(dividerString).toMutableList()
        s.removeAll(setOf("", null))
        return s
    }
}