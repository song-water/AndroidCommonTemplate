package com.water.song.template.app

import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Water-Song
 */
@RunWith(DataProviderRunner::class)
class KtDataProviderParameterizedTest {
    /**
     * 参数化测试方法一
     */
    @DataProvider(value = ["true,  10086, 1234", "false, 10086, 123488"])
    @Test
    fun test_normalParameterizedTes(expected: Boolean, leftNum: Int, rightNum: Int) {
        Assert.assertEquals(expected, leftNum > rightNum)
    }

    /**
     * 参数化测试方法二
     */
    @Test
    @UseDataProvider("dataProviderPlus")
    fun testPlus(a: Int, b: Int, expected: Int) {
        // Given:
        // When:
        val result = a + b

        // Then:
        Assert.assertEquals(expected.toLong(), result.toLong())
    }

    companion object {
        @DataProvider
        @JvmStatic
        fun dataProviderPlus(): Array<Array<Any>> {
            return arrayOf(
                arrayOf(0, 0, 0),
                arrayOf(1, 1, 2),
                arrayOf(1, 4, 5)
            )
        }
    }
}