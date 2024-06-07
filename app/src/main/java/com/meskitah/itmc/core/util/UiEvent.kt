package com.meskitah.itmc.core.util

sealed class UiEvent {
    data object Success : UiEvent()
    data object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}