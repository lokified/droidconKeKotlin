package com.android254.presentation.speakers

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android254.domain.models.ResourceResult
import com.android254.domain.repos.SpeakersRepo
import com.android254.presentation.models.SpeakerUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SpeakerDetailsScreenUiState {

    object Loading : SpeakerDetailsScreenUiState

    data class Success(val speaker: SpeakerUI ) : SpeakerDetailsScreenUiState

    data class Error(val message: String ) : SpeakerDetailsScreenUiState

    data class SpeakerNotFound(val message: String):SpeakerDetailsScreenUiState
}

@HiltViewModel
class SpeakerDetailsScreenViewModel @Inject constructor(
    private val speakersRepo: SpeakersRepo,
    private val savedStateHandle: SavedStateHandle,
) :ViewModel(){

    private val _uiState = MutableStateFlow<SpeakerDetailsScreenUiState>(SpeakerDetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Int>("speakerId")?.let{
            viewModelScope.launch {
                getSpeakerById(id = it)
            }
        }
    }

    suspend fun getSpeakerById(id: Int) {
        when (val result = speakersRepo.getSpeakerById(id)) {
            is ResourceResult.Success -> {
                val data = result.data
                if (data == null) {
                    _uiState.value = SpeakerDetailsScreenUiState.SpeakerNotFound(message = "Speaker Not found")

                } else {
                    val speaker = SpeakerUI(
                        id = 1,
                        imageUrl = data.avatar,
                        name = data.name,
                        tagline = data.tagline,
                        bio = data.biography,
                        twitterHandle = data.twitter
                    )
                    _uiState.value = SpeakerDetailsScreenUiState.Success(speaker = speaker)

                }
            }
            is ResourceResult.Error -> {
                _uiState.value = SpeakerDetailsScreenUiState.Error(message = result.message)
            }
            else -> {}
        }
    }
}