package com.example.notes_app.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes_app.data.data_source.NoteDatabase
import com.example.notes_app.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(noteDatabase: NoteDatabase) : ViewModel() {

    val dao = noteDatabase.noteDao
    private val isSortedByDate = MutableStateFlow(true)
    private var notes = isSortedByDate.flatMapLatest {
        if (it) {
            dao.getAllNotesOrderByDate()
        } else {
            dao.getAllNotesOrderByTitle()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(), emptyList()
        )
    }
    val _state = MutableStateFlow(NoteState())
    val state= combine(_state,isSortedByDate,notes){
        state,isSortedByDate,notes->
        state.copy(
            notes=notes
        )
    }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),NoteState())


    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.delete(event.note)
                }
            }
            is NoteEvent.SaveNote -> {
                    val note= Note(
                        title = state.value.title.value,
                        content = state.value.content.value,
                        date = System.currentTimeMillis()
                    )
                    viewModelScope.launch {
                        dao.upsert(note)
                        _state.value=state.value.copy(
                            title = mutableStateOf(""),
                            content =mutableStateOf("")
                        )
                    }
                }
            is NoteEvent.SortNotes -> {
                isSortedByDate.value = !isSortedByDate.value
            }
        }
    }
}