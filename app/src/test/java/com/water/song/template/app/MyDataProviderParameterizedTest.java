package com.water.song.template.app;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Water-Song
 */
@RunWith(DataProviderRunner.class)
public class MyDataProviderParameterizedTest {

    /**
     * 参数化测试方法一
     * */
    @DataProvider(value = {
            "true,  10086, 1234",
            "false, 10086, 123488"
    })
    @Test
    public void test_normalParameterizedTes(boolean expected, int leftNum, int rightNum) {
        assertEquals(expected, leftNum > rightNum);
    }

    /**
     * 参数化测试方法二
     * */
    @DataProvider
    @NonNull
    public static Object[][] dataProviderPlus() {
        return new Object[][]{
                {0, 0, 0},
                {1, 1, 2},
                {1, 4, 5},
                /* ... */
        };
    }

    @Test
    @UseDataProvider("dataProviderPlus")
    public void testPlus(int a, int b, int expected) {
        // Given:
        // When:
        int result = a + b;

        // Then:
        assertEquals(expected, result);
    }
}
