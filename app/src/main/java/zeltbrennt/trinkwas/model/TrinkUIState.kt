package zeltbrennt.trinkwas.model

data class TrinkUIState(
    val target: Int = 3000,
    val amount: Int = 0,
    val stepSize: Float = 200F,
    val currentPercent: Int = 0
)
