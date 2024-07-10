package com.example.notes_app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)val id: Int=0,
    var title: String,
    var content: String,
    var date: Long
)
