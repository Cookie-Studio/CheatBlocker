package me.liuli.cb.utils

import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object FileUtils {
    fun readFile(file: File): String {
        try {
            return String(Files.readAllBytes(file.toPath()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun writeFile(path: File, text: String) {
        try {
            val writer: Writer = BufferedWriter(OutputStreamWriter(FileOutputStream(path), StandardCharsets.UTF_8))
            writer.write(text)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTextFromResource(resourceName: String): String {
        val inputStream = FileUtils::class.java.classLoader.getResourceAsStream(resourceName) ?: return ""
        try {
            return getTextFromInputStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getTextFromInputStream(inputStream: InputStream): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, length)
        }
        byteArrayOutputStream.close()
        inputStream.close()
        return byteArrayOutputStream.toString("UTF-8")
    }
}