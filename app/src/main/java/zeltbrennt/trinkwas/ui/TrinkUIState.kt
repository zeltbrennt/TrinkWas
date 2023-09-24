package zeltbrennt.trinkwas.ui

import zeltbrennt.trinkwas.data.TrinkEvent
import zeltbrennt.trinkwas.util.getCurrentDateTime
import zeltbrennt.trinkwas.util.toString
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

data class TrinkUIState(
    val target: Int = 3000,
    val amount: Int = 0,
    val glass: Float = 200F,
    val currentPercent: Int = 0,
    val start: Duration = 6.hours + 30.minutes,
    val end: Duration = 21.hours,
    val nextAlert: Duration = start,
    val interval: Duration = 1.hours
)

fun TrinkUIState.toEvent(): TrinkEvent {
    val date = getCurrentDateTime()
    return TrinkEvent(
        date = date.toString("yyyy-MM-dd"),
        time = date.toString("HH:mm:ss"),
        amount = glass.toInt(),
        type = "Water"
    )
}