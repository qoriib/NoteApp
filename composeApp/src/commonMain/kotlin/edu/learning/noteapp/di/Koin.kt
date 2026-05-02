package edu.learning.noteapp.di

import edu.learning.noteapp.api.GeminiService
import edu.learning.noteapp.api.NoteAIAssistant
import edu.learning.noteapp.db.NoteDatabase
import edu.learning.noteapp.ui.AIViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule, platformModule)
    }

// For iOS
fun initKoin() = initKoin {}

val commonModule = module {
    single { 
        val driver = get<edu.learning.noteapp.db.DatabaseDriverFactory>().createDriver()
        NoteDatabase(driver)
    }
    single { get<NoteDatabase>().noteQueries }
    
    // AI Integration
    single { GeminiService(apiKey = "YOUR_GEMINI_API_KEY") }
    single { NoteAIAssistant(get()) }
    viewModel { AIViewModel(get()) }
}

expect val platformModule: Module
