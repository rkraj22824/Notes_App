package com.example.notes_app.di

import android.app.Application
import androidx.room.Room
import com.example.notes_app.data.data_source.NoteDao
import com.example.notes_app.data.data_source.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabse(application: Application): NoteDatabase {
        return Room.databaseBuilder(application, NoteDatabase::class.java, "note_database.db")
            .build()
    }
}