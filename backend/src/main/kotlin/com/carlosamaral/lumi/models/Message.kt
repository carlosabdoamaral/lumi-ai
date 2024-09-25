package com.carlosamaral.lumi.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Message(
    @Id @GeneratedValue var messageId: UUID? = null,

    @Column(nullable = false) var chatId: UUID, // Apenas o ID do chat

    @Column(nullable = false) var text: String,

    var negative: Float = 0.0f,
    var neutral: Float = 0.0f,
    var positive: Float = 0.0f,
    var compound: Float = 0.0f,
    var promptTokens: Int = 0,
    var completionTokens: Int = 0,
    var totalTokens: Int = 0,

    @Column(nullable = false, updatable = false) var createdAt: Date = Date()
)
