package edu.learning.noteapp.di

import edu.learning.noteapp.db.NoteDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

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
}

expect val platformModule: Module
