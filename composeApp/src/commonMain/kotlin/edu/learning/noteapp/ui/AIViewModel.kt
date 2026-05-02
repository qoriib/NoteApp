package edu.learning.noteapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.learning.noteapp.api.NoteAIAssistant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AIUiState {
    object Idle : AIUiState()
    object Loading : AIUiState()
    data class Success(val response: String) : AIUiState()
    data class Error(val message: String) : AIUiState()
}

class AIViewModel(private val assistant: NoteAIAssistant) : ViewModel() {
    private val _uiState = MutableStateFlow<AIUiState>(AIUiState.Idle)
    val uiState: StateFlow<AIUiState> = _uiState.asStateFlow()

    fun askAI(prompt: String, context: String? = null) {
        if (prompt.isBlank()) return

        viewModelScope.launch {
            _uiState.value = AIUiState.Loading
            val result = assistant.askAssistant(prompt, context)
            _uiState.value = result.fold(
                onSuccess = { AIUiState.Success(it) },
                onFailure = { AIUiState.Error(it.message ?: "Unknown error occurred") }
            )
        }
    }

    fun reset() {
        _uiState.value = AIUiState.Idle
    }
}
