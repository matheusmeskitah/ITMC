package com.meskitah.itmc.presentation.flicker_screen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meskitah.itmc.R
import com.meskitah.itmc.core.util.UiEvent
import com.meskitah.itmc.core.util.UiText
import com.meskitah.itmc.domain.use_case.FlickerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FlickerViewModel @Inject constructor(
    private val useCases: FlickerUseCases
) : ViewModel() {

    var state by mutableStateOf(FlickerState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: FlickerEvent) {
        when (event) {
            is FlickerEvent.OnLoadImages -> loadSports(event.query, event.context)

            is FlickerEvent.OnReloadImages -> reloadSports(event.context)

            is FlickerEvent.OnImageClick -> {
//                state = state.copy(
//                    sports = state.sports.map {
//                        if (it.id == event.sport.id) {
//                            it.copy(isExpanded = !it.isExpanded)
//                        } else it
//                    }.toMutableList()
//                )
            }
        }
    }

    private fun loadSports(query: String, context: Context) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            useCases
                .getImages(query, context)
                .onSuccess { flicker ->
                    state = state.copy(
                        flickerImage = flicker,
                        isLoading = false
                    )

                    _uiEvent.send(UiEvent.Success)
                }
                .onFailure {
                    state = state.copy(isLoading = false, isError = true)
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.error_oops_something_went_wrong)
                        )
                    )
                }
        }
    }

    private fun reloadSports(context: Context) {
        viewModelScope.launch {
            useCases
                .getImages("", context)
                .onSuccess { flicker ->
                    state = state.copy(flickerImage = flicker)
                    _uiEvent.send(UiEvent.Success)
                }
                .onFailure {
                    state = state.copy(isError = true)
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(UiText.DynamicString(it.localizedMessage ?: ""))
                    )
                }
        }
    }
}