package com.water.song.template.lint.lintrules.detector

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Ignore
import org.junit.Test

/**
 * @author Water-Song
 */
@Suppress("UnstableApiUsage")
class LazyModeDetectorTest {
    @Test
    @Ignore("中文编码问题，导致case失败")
    fun testLazyModeDetectorWithoutParenthesis() {
        lint()
            .files(
                kotlin(
                    """
        package foo
        
        class TestDraftLazy {
            val draftLazyInstance by lazy {
                "draftLazyInstance"
            }
        }
          """
                ).indented()
            )
            .issues(LazyModeDetector.ISSUE)
            .run()
            .expect(
                """
|src/foo/TestDraftLazy.kt:4: Error: ${LazyModeDetector.REPORT_MSG} [${LazyModeDetector.ISSUE_ID}]
|    val draftLazyInstance by lazy {
|                             ^
|1 errors, 0 warnings
            |""".trimMargin()
            )
    }

    @Test
    @Ignore("中文编码问题，导致case失败")
    fun testLazyModeDetectorWithEmptyParenthesis() {
        lint()
            .files(
                kotlin(
                    """
        package foo
        
        class TestDraftLazy {
            val draftLazyInstance by lazy() {
                "draftLazyInstance"
            }
        }
          """
                ).indented()
            )
            .issues(LazyModeDetector.ISSUE)
            .run()
            .expect(
                """
|src/foo/TestDraftLazy.kt:4: Error: ${LazyModeDetector.REPORT_MSG} [${LazyModeDetector.ISSUE_ID}]
|    val draftLazyInstance by lazy() {
|                             ^
|1 errors, 0 warnings
            |""".trimMargin()
            )
    }

    @Test
    @Ignore("中文编码问题，导致case失败")
    fun testLazyModeDetectorWithEmptyWrapParenthesis() {
        lint()
            .files(
                kotlin(
                    """
        package foo
        
        class TestDraftLazy {
            val draftLazyInstance by lazy(
            ) {
                "draftLazyInstance"
            }
        }
          """
                ).indented()
            )
            .issues(LazyModeDetector.ISSUE)
            .run()
            .expect(
                """
|src/foo/TestDraftLazy.kt:4: Error: ${LazyModeDetector.REPORT_MSG} [${LazyModeDetector.ISSUE_ID}]
|    val draftLazyInstance by lazy(
|                             ^
|1 errors, 0 warnings
            |""".trimMargin()
            )
    }

    @Test
    fun testLazyModeDetectorWithLazyThreadSafetyModeSet() {
        lint()
            .files(
                kotlin(
                    """ 
        package foo
        
        class TestDraftLazy {
            val draftLazyInstance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
                "draftLazyInstance"
            }
        }
          """
                ).indented()
            )
            .issues(LazyModeDetector.ISSUE)
            .run()
            .expect(
                """
|No warnings.
            |""".trimMargin()
            )
    }
}