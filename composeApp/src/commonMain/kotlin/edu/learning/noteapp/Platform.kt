package edu.learning.noteapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform