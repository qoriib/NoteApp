package edu.learning.noteapp.api

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String? = null
)

@Serializable
data class Part(
    val text: String
)

@Serializable
data class GenerationConfig(
    val temperature: Float? = null,
    val topK: Int? = null,
    val topP: Float? = null,
    val maxOutputTokens: Int? = null,
    val stopSequences: List<String>? = null
)

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>? = null,
    val promptFeedback: PromptFeedback? = null
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String? = null,
    val index: Int? = null,
    val safetyRatings: List<SafetyRating>? = null
)

@Serializable
data class SafetyRating(
    val category: String,
    val probability: String
)

@Serializable
data class PromptFeedback(
    val safetyRatings: List<SafetyRating>? = null
)
