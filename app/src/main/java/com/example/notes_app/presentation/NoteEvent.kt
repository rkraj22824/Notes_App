package com.example.notes_app.presentation

import android.icu.text.CaseMap.Title
import com.example.notes_app.domain.model.Note

sealed interface NoteEvent {
     object SortNotes : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
    data class SaveNote(val title: String, var content: String) : NoteEvent

}