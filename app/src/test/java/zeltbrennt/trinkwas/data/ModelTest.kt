package zeltbrennt.trinkwas.data

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class ModelTest {

    @Test
    fun calcIntervall_correctly() {
        val glass = 100.0
        val delta: Duration = 10.hours
        val target = 1000.0
        val correctInterval: Duration = 1.hours
        assertEquals(correctInterval, calcIntervalFromGlassSize(glass, delta, target))
    }

    @Test
    fun calcIntervall_correctly2() {
        val glass = 123.0
        val delta: Duration = 13.hours + 12.minutes
        val target = 987.6
        val correctInterval: Duration = 95.minutes
        assertEquals(correctInterval, calcIntervalFromGlassSize(glass, delta, target))
    }

    @Test
    fun calcGlass_correctly1() {
        val intervall: Duration = 1.hours
        val timeSpan: Duration = 10.hours
        val target = 1000.0
        val correctGlass = 100.0
        assertEquals(correctGlass, calcGlassFromInterval(intervall, timeSpan, target), 0.001)
    }

    @Test
    fun calcGlass_correctly2() {
        val intervall: Duration = 95.minutes
        val timeSpan: Duration = 13.hours + 12.minutes
        val target = 987.6
        val correctGlass = 150.0
        assertEquals(correctGlass, calcGlassFromInterval(intervall, timeSpan, target), 0.001)
    }

    @Test
    fun calcBigGulp_correctly1() {
        val intervall: Duration = 1.hours
        val timeSpan: Duration = 10.hours
        val target = 1000.0
        val alertNumber = 0
        val glass = 100.0
        val correctGlass = 100.0
        assertEquals(
            correctGlass,
            calcOneTimeBigGulp(
                intervall,
                timeSpan,
                target,
                glass = glass,
                pastEvents = alertNumber
            ),
            0.001
        )
    }


    @Test
    fun calcBigGulp_correctly2() {
        val intervall: Duration = 1.hours
        val timeSpan: Duration = 10.hours
        val target = 1000.0
        val alertNumber = 5
        val glass = 100.0
        val correctGlass = glass + glass * alertNumber
        assertEquals(
            correctGlass,
            calcOneTimeBigGulp(
                intervall,
                timeSpan,
                target,
                glass = glass,
                pastEvents = alertNumber
            ),
            0.001
        )
    }
}