package com.water.song.template.app

import android.net.Uri
import android.os.Bundle
import com.water.song.template.unittests.DataProviderUtils
import org.approvaltests.Approvals
import org.junit.Test

/**
 * @author Water-Song
 */
data class ApprovalsDemo(
    val yes: String,
    val no: Int,
    val what: Boolean
)

class ApprovalsDemoTest {
    @Test
    fun test_ApprovalsDemo() {
        val demo = ApprovalsDemo("hello world", 110, true)
        Approvals.verify(demo)
    }

    @Test
    fun test_ApprovalsDemo_getFromJson() {
        val json = DataProviderUtils.readJsonForMethod()
        println("json:$json")
        /**
         * 这个可以用来方便的给测试提供数据，方便将用例和数据放到一起维护
         *
         * 这个 case 执行后将会打印:
        json:{
        "yes": "how do you do",
        "no": 10086,
        "what": false
        }
         * */
    }
}