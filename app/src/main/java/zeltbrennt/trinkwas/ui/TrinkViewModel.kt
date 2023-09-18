package zeltbrennt.trinkwas.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object TrinkViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TrinkUIState())
    val uiState: StateFlow<TrinkUIState> = _uiState.asStateFlow()

    fun updateUI() {
        _uiState.update { currentState ->
            val newAmount = currentState.amount + currentState.stepSize.toInt()
            currentState.copy(
                amount = newAmount,
                currentPercent = (newAmount / currentState.target * 100)
            )
        }
    }

    fun changeStepSize(newValue: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                stepSize = newValue
            )
        }
    }

    fun reset() {
        _uiState.update { currentState ->
            currentState.copy(
                amount = 0,
                currentPercent = 0
            )
        }
    }

}