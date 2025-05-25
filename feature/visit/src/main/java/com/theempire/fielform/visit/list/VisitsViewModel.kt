package com.theempire.fielform.visit.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.theempire.fielform.domain.visits.network.NetworkListener
import com.theempire.fielform.domain.visits.sync.UploadManager
import com.theempire.fielform.domain.visits.usecases.GetAllVisitsUseCase
import com.theempire.fielform.domain.visits.usecases.GetVisitsByStatusUseCase
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
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
class VisitsViewModel @Inject constructor(
    private val getAllVisitsUseCase: GetAllVisitsUseCase,
    private val getVisitsByStatusUseCase: GetVisitsByStatusUseCase,
    private val networkListener: NetworkListener,
    private val uploadManager: UploadManager
): ViewModel() {


    private val _uiState : MutableStateFlow<VisitsUiState> = MutableStateFlow<VisitsUiState>(VisitsUiState.List(getAllVisitsUseCase()))
    val uiState : StateFlow<VisitsUiState> =
        _uiState
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), VisitsUiState.Empty)

    private val _events = MutableSharedFlow<VisitsUiEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _effect = MutableSharedFlow<VisitsUiEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect = _effect.asSharedFlow()

    fun onEvent(event: VisitsUiEvent) {
        _events.tryEmit(event)
    }

    init {
        viewModelScope.launch { _events.collect { handleEvent(it) } }
        networkListener.startListening(
            onNetworkRestored = { _events.tryEmit(VisitsUiEvent.NetworkRestored) },
            onNetworkLost = { _events.tryEmit(VisitsUiEvent.NetworkLost) }
        )
        uploadManager.schedulePeriodicSync(
            onProgress = { _events.tryEmit(VisitsUiEvent.Refreshing) },
            onFinished = { _events.tryEmit(VisitsUiEvent.StoppedRefreshing) }
        )
    }

    private fun handleEvent(event: VisitsUiEvent) {
        when(event) {
            is VisitsUiEvent.FilterList -> {
                _uiState.update { VisitsUiState.List(list = getVisitsByStatusUseCase(event.status)) }
            }
            VisitsUiEvent.ClearFilters -> {
                _uiState.update { VisitsUiState.List(list = getAllVisitsUseCase()) }
            }

            VisitsUiEvent.NetworkLost -> {
                _effect.tryEmit(VisitsUiEffect.NetworkLostMessage)
            }
            VisitsUiEvent.NetworkRestored -> {
                _effect.tryEmit(VisitsUiEffect.NetworkRestoredMessage)
            }

            VisitsUiEvent.SyncVisits -> {
                _effect.tryEmit(VisitsUiEffect.ShowLoading)
                uploadManager.scheduleSync {
                    _effect.tryEmit(VisitsUiEffect.HideLoading)
                }
            }

            VisitsUiEvent.NavigateToAddVisit -> _effect.tryEmit(VisitsUiEffect.NavigateToAddVisit)

            VisitsUiEvent.Refreshing -> _effect.tryEmit(VisitsUiEffect.ShowLoading)

            VisitsUiEvent.StoppedRefreshing -> _effect.tryEmit(VisitsUiEffect.HideLoading)
        }
    }
}

sealed interface VisitsUiEffect {
    data object NetworkLostMessage : VisitsUiEffect
    data object NetworkRestoredMessage : VisitsUiEffect
    data object NavigateToAddVisit : VisitsUiEffect
    data object ShowLoading : VisitsUiEffect
    data object HideLoading : VisitsUiEffect
}

sealed interface VisitsUiEvent {
    data class FilterList(val status: VisitStatus) : VisitsUiEvent
    data object ClearFilters : VisitsUiEvent
    data object NetworkLost : VisitsUiEvent
    data object NetworkRestored : VisitsUiEvent
    data object SyncVisits : VisitsUiEvent
    data object NavigateToAddVisit : VisitsUiEvent
    data object Refreshing : VisitsUiEvent
    data object StoppedRefreshing : VisitsUiEvent
}

sealed class VisitsUiState {
    object Loading : VisitsUiState()
    object Error : VisitsUiState()
    object Empty : VisitsUiState()
    class List(val list: Flow<PagingData<Visit>>) : VisitsUiState()
}