package edu.learning.noteapp.api

class NoteAIAssistant(private val geminiService: GeminiService) {
    
    private val systemPrompt = """
        You are a helpful AI assistant for a Note App. 
        Your task is to help users with their notes. 
        You can summarize notes, improve their writing, or answer questions about the note content.
        Always be concise and professional.
        If you are asked to summarize, provide a brief bulleted list.
        If you are asked to improve writing, provide a more polished version of the text.
    """.trimIndent()

    suspend fun askAssistant(userPrompt: String, noteContent: String? = null): Result<String> {
        val fullPrompt = if (noteContent != null) {
            "${systemPrompt}\n\nContext Note Content:\n${noteContent}\n\nUser Question: ${userPrompt}"
        } else {
            "${systemPrompt}\n\nUser Question: ${userPrompt}"
        }
        
        return geminiService.generateResponse(fullPrompt)
    }
}
