package edu.learning.noteapp.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class GeminiService(
    private val apiKey: String
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent"

    suspend fun generateResponse(prompt: String): Result<String> {
        return try {
            val response: GeminiResponse = client.post("$baseUrl?key=$apiKey") {
                contentType(ContentType.Application.Json)
                setBody(
                    GeminiRequest(
                        contents = listOf(
                            Content(
                                parts = listOf(Part(text = prompt)),
                                role = "user"
                            )
                        )
                    )
                )
            }.body()

            val text = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (text != null) {
                Result.success(text)
            } else {
                Result.failure(Exception("Empty response from AI"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
