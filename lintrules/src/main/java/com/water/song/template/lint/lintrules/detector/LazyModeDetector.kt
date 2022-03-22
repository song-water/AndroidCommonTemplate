package com.water.song.template.lint.lintrules.detector

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.util.isMethodCall

/**
 * @author Water-Song
 */
@Suppress("UnstableApiUsage")
class LazyModeDetector : Detector(), Detector.UastScanner {
    companion object {

        const val KtPropertyDelegateType = "PROPERTY_DELEGATE"
        const val ISSUE_ID = "LazyModeDetector"
        private const val ISSUE_BRIEF = "lazy请指定明确的LazyThreadSafetyMode"
        private const val ISSUE_EXPLAIN = "lazy() 默认情况下会指定LazyThreadSafetyMode.SYNCHRONIZED。" +
                "这可能会造成不必要线程安全的开销，应该根据实际情况，指定合适的model来避免不需要的同步锁"
        const val REPORT_MSG = ISSUE_EXPLAIN

        @JvmField
        val ISSUE = Issue.create(
            ISSUE_ID,
            ISSUE_BRIEF,
            ISSUE_EXPLAIN,
            Category.CORRECTNESS,
            6,
            Severity.ERROR,
            Implementation(LazyModeDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableUastTypes() = listOf(
        UCallExpression::class.java
    )

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return LazyModeDetectorHandler(context)
    }

    class LazyModeDetectorHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitCallExpression(node: UCallExpression) {
            if (!node.isMethodCall()) {
                return
            }

            if (isKtPropertyDelegateCall(node)) {
                val propertyDelegateIdentifier = node.sourcePsi?.firstChild
                if (isLazyIdentifier(propertyDelegateIdentifier)) {
                    val sibling = findNextFirstNonWhiteSpaceSibling(propertyDelegateIdentifier)
                    when (sibling?.originalElement.toString()) {
                        "LAMBDA_ARGUMENT" -> {
                            reportMissLazyModeMsg(node)
                        }
                        "VALUE_ARGUMENT_LIST" -> {
                            if (isEmptyParenthesis(sibling)) {
                                reportMissLazyModeMsg(node)
                            }
                        }
                        else -> {
                            // Nothing
                        }
                    }
                }
            }
        }

        private fun reportMissLazyModeMsg(node: UCallExpression) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                REPORT_MSG
            )
        }

        private fun isRightParenthesis(element: PsiElement?): Boolean {
            return "PsiElement(RPAR)" == element?.originalElement.toString()
        }

        private fun isEmptyParenthesis(element: PsiElement?): Boolean {
            if ("VALUE_ARGUMENT_LIST" == element?.originalElement.toString()) {
                val firstChild  = element?.firstChild
                val sibling = findNextFirstNonWhiteSpaceSibling(firstChild)
                if (isRightParenthesis(sibling)) {
                    return true
                }
            }
            return false
        }

        private fun isLazyIdentifier(firstChild: PsiElement?) = "lazy" == firstChild?.text

        private fun isKtPropertyDelegateCall(node: UCallExpression) =
            node.sourcePsi?.context?.toString() == KtPropertyDelegateType

        private fun findNextFirstNonWhiteSpaceSibling(element: PsiElement?): PsiElement? {
            element ?: return null
            var next = element.nextSibling
            while (next is PsiWhiteSpace) {
                next = next.nextSibling
            }
            return next
        }
    }
}