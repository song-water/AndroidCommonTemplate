package com.water.song.template.unittests

import com.spun.util.tests.TestUtils
import org.approvaltests.namer.AttributeStackSelector
import java.io.File

/**
 * @author Water-Song
 * 利用 Approvals 的功能来获取测试数据，
 */
object DataProviderUtils {

    @JvmStatic
    fun readFile(fullPath: String): String {
        return File(fullPath).readText(Charsets.UTF_8)
    }

    @JvmStatic
    fun readDataFromCurrentDir(fileName: String): String {
        val methodTrace = TestUtils.getCurrentFileForMethod(AttributeStackSelector())
        val absPath = methodTrace.sourceFile.absolutePath
        val absFile = File(absPath + File.separatorChar + fileName)
        return if (absFile.exists() && absFile.isFile) {
            readFile(absFile.absolutePath)
        } else {
            ""
        }
    }

    @JvmStatic
    fun readDataForMethod(): String {
        val methodTrace = TestUtils.getCurrentFileForMethod(AttributeStackSelector())
        val absPath = methodTrace.sourceFile.absolutePath
        val fileName = "${methodTrace.className}.${methodTrace.methodName}.txt"
        val absFile = File(absPath + File.separatorChar + fileName)
        return if (absFile.exists() && absFile.isFile) {
            readFile(absFile.absolutePath)
        } else {
            ""
        }
    }

    @JvmStatic
    fun readJsonForMethod(): String {
        val methodTrace = TestUtils.getCurrentFileForMethod(AttributeStackSelector())
        val absPath = methodTrace.sourceFile.absolutePath
        val fileName = "${methodTrace.className}.${methodTrace.methodName}.json"
        val absFile = File(absPath + File.separatorChar + fileName)
        return if (absFile.exists() && absFile.isFile) {
            readFile(absFile.absolutePath)
        } else {
            ""
        }
    }
}