package com.example.notes_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes_app.presentation.AddNoteScreen
import com.example.notes_app.presentation.NoteViewModel
import com.example.notes_app.presentation.NoteScreen
import com.example.notes_app.ui.theme.Notes_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Notes_AppTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewmodel= hiltViewModel<NoteViewModel>()
                    val state by viewmodel.state.collectAsState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "notes_screen"
                        ) {
                            composable("notes_screen") {
                                NoteScreen(navController = navController, state = state, onEvent = viewmodel::onEvent)
                            }
                            composable("add_note_screen") {
                                AddNoteScreen(navController=navController,state =state,onEvent = viewmodel::onEvent)
                            }

                        }
                    }
                }
            }
        }
    }
}
