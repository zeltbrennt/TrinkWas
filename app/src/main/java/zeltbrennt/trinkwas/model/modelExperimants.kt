package zeltbrennt.trinkwas.model


import kotlin.math.ceil
import kotlin.math.floor
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun main() {
    val start: Duration = 6.hours + 30.minutes
    val end: Duration = 22.hours
    val interval: Duration = 45.minutes
    val duration = end - start
    val target = 3000.0
    val amount = 300.0
    val calculatedGlassSize = calcGlassFromInterval(interval, duration, target)
    val baseGlassSize = 300.0
    println("Given an interval of $interval, I need to drink ${calculatedGlassSize}ml each time, so that after $duration I will have reached my goal of ${target}ml")
    val calcedInterval = calcIntervalFromGlassSize(baseGlassSize, duration, target)
    println("On the other hand, if I just have one glass of ${baseGlassSize}ml and a target of ${target}ml, then I have a pace of $calcedInterval")
    val pastEvents = 5
    println("Let's say, after $pastEvents reminders (${interval * pastEvents}) I managed to drink only ${amount}ml. ")
    println(
        "That means, on average, I have to drink ${
            calcGlassFromInterval(
                interval,
                duration,
                target,
                amount,
                pastEvents
            )
        }ml"
    )
    println(
        "But I could also drink ${
            calcOneTimeBigGulp(
                interval,
                duration,
                target,
                calculatedGlassSize,
                amount,
                pastEvents
            )
        }ml now, to be back on track of $calculatedGlassSize ml every $interval"
    )

}

fun calcIntervalFromGlassSize(
    glass: Double,
    delta: Duration,
    target: Double
): Duration {
    val numberOfEvents = target / glass
    val durationRaw = delta / numberOfEvents
    return 5.minutes * floor(durationRaw / 5.minutes)
}

fun calcGlassFromInterval(
    interval: Duration,
    timeSpan: Duration,
    target: Double,
    amount: Double = 0.0,
    pastEvents: Int = 0
): Double {
    val numberOfEvents = timeSpan / interval - pastEvents
    val glassRaw = (target - amount) / numberOfEvents
    return 50 * ceil(glassRaw / 50)
}

fun calcOneTimeBigGulp(
    interval: Duration,
    timeSpan: Duration,
    target: Double,
    glass: Double,
    amount: Double = 0.0,
    pastEvents: Int = 0
): Double {
    val numberOfRemainingEvents = timeSpan / interval - pastEvents - 1
    val glassRaw = amount + numberOfRemainingEvents * glass
    return (target - (50 * floor(glassRaw / 50))).coerceAtLeast(glass)
}
