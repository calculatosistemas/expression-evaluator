package br.com.calculato

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import java.math.BigDecimal

private val SALARIO_DIA = BigDecimal.valueOf(38.8)

class ExpressionsTest : StringSpec({
    "test basic math operations" {
        with(Expressions()) {
            setPrecision(6)
            eval("13232 * 23455") shouldBe BigDecimal.valueOf(310356560)
            eval("12/(3+20)") shouldBe BigDecimal.valueOf(0.521739)
            eval("12/3+20") shouldBe BigDecimal.valueOf(24)
            eval("1,2/3+20") shouldBe BigDecimal.valueOf(2.4)
            eval("2 + 3 - (12/3+ (20 * 4))") shouldBe BigDecimal.valueOf(-79)
        }
    }
    "test with variables" {
        with(Expressions()) {
            define("SALDIA", SALARIO_DIA)
            eval("1164/SALDIA*30").toPlainString() shouldBeEqualIgnoringCase BigDecimal.valueOf(900).toPlainString()
        }
    }
    "test that scientific notation BigDecimals are parsed and equivalent to the plain representation" {
        val expr = Expressions()
        val scival = BigDecimal("1E+7")
        expr.define("SCIVAL", scival)

        scival.toPlainString() shouldBeEqualIgnoringCase expr.eval("SCIVAL").toPlainString()
    }
    "test Scanner will scan scientific form correctly" {
        val expr = Expressions()
        BigDecimal("1e+7").toPlainString() shouldBeEqualIgnoringCase expr.eval("1E+7").toPlainString()
        BigDecimal("1e-7").toPlainString() shouldBeEqualIgnoringCase expr.eval("1E-7").toPlainString()
        BigDecimal(".101e+2").toPlainString() shouldBeEqualIgnoringCase expr.eval(".101e+2").toPlainString()
        BigDecimal(".123e2").toPlainString() shouldBeEqualIgnoringCase expr.eval(".123e2").toPlainString()
        BigDecimal("3212.123e-2").toPlainString() shouldBeEqualIgnoringCase expr.eval("3212.123e-2").toPlainString()
    }
})
