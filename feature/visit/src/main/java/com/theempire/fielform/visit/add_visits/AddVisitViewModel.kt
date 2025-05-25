package com.theempire.fielform.visit.add_visits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theempire.fielform.domain.visits.sync.UploadManager
import com.theempire.fielform.domain.visits.usecases.SaveVisitUseCase
import com.theempire.fielform.model.Visit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVisitViewModel @Inject constructor(
    private val saveVisitUseCase: SaveVisitUseCase,
    private val uploadManager: UploadManager
 ) : ViewModel() {

    private val _uiState : MutableStateFlow<AddVisitUiState> = MutableStateFlow<AddVisitUiState>(AddVisitUiState())
    val uiState : StateFlow<AddVisitUiState> =
        _uiState
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AddVisitUiState())

    private val _effect = MutableSharedFlow<AddVisitUiEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect = _effect.asSharedFlow()

    private val _events = MutableSharedFlow<AddVisitUiEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            _events.collect {
                handleEvent(it)
            }
        }
    }

    fun onEvent(event: AddVisitUiEvent) {
        _events.tryEmit(event)
    }

    fun handleEvent(event: AddVisitUiEvent) {
        when(event) {
            is AddVisitUiEvent.UpdateAgentName -> _uiState.update { it.copy(agentName = event.value) }
            is AddVisitUiEvent.UpdateComment -> _uiState.update { it.copy(comment = event.value) }
            is AddVisitUiEvent.UpdateSiteName -> _uiState.update { it.copy(siteName = event.value) }
            is AddVisitUiEvent.Submit -> { saveVisit() }
        }
    }

    fun saveVisit() {
        viewModelScope.launch {
            val state = uiState.value
            val visit = Visit(siteName = state.siteName, agentName = state.agentName, comment = state.comment)
            saveVisitUseCase(visit = visit)
            uploadManager.scheduleSync()
            _effect.emit(AddVisitUiEffect.PopUpBackstack)
        }
    }
}

data class AddVisitUiState(
    val siteName : String = "",
    val agentName: String = "",
    val comment: String = "")

sealed interface AddVisitUiEffect {
    object PopUpBackstack: AddVisitUiEffect
}

sealed interface AddVisitUiEvent {
    data class UpdateSiteName(val value: String) : AddVisitUiEvent
    data class UpdateAgentName(val value: String) : AddVisitUiEvent
    data class UpdateComment(val value: String) : AddVisitUiEvent
    data object Submit : AddVisitUiEvent
}