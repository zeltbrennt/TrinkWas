package zeltbrennt.trinkwas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zeltbrennt.trinkwas.TrinkWasApplication
import zeltbrennt.trinkwas.data.EventRepository
import zeltbrennt.trinkwas.util.getCurrentDateTime
import zeltbrennt.trinkwas.util.toString

class TrinkViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TrinkUIState())
    val uiState: StateFlow<TrinkUIState> = _uiState.asStateFlow()

    suspend fun retrieveTodayAmount() {
        val date = getCurrentDateTime()
        val currentAmount = eventRepository.getTodayAmount(date.toString("yyyy-MM-dd"))
        _uiState.update { currentState ->
            currentState.copy(
                amount = currentAmount,
                currentPercent = (currentAmount / currentState.target.toFloat() * 100).toInt()
            )
        }
    }

    suspend fun drinkGlass() {
        _uiState.update { currentState ->
            val newAmount = currentState.amount + currentState.glass.toInt()
            currentState.copy(
                amount = newAmount,
                currentPercent = (newAmount / currentState.target.toFloat() * 100).toInt(),
                nextAlert = currentState.nextAlert + currentState.interval
            )
        }
        eventRepository.insertEvent(_uiState.value.toEvent())
    }

    fun changeStepSize(newValue: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                glass = newValue
            )
        }
    }

    fun showTimeOfNextEvent(): String {
        return _uiState.value.nextAlert.toString()
    }

    suspend fun reset() {
        _uiState.update { currentState ->
            currentState.copy(
                amount = 0,
                currentPercent = 0
            )
        }
        eventRepository.deleteData()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TrinkWasApplication)
                val eventRepository = application.container.eventRepository
                TrinkViewModel(eventRepository)
            }
        }
    }

}