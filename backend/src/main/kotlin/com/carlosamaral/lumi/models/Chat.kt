package com.carlosamaral.lumi.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Chat(
    @Id
    @GeneratedValue
    var chatId: UUID? = null,

    @Column(nullable = false)
    var accountId: UUID, // Apenas o ID da conta

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, updatable = false)
    var createdAt: Date = Date()
)
