package edu.learning.noteapp

import androidx.compose.ui.window.ComposeUIViewController
import edu.learning.noteapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { 
    App() 
}
