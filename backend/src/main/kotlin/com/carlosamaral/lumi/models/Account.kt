package com.carlosamaral.lumi.models

import jakarta.persistence.*
import java.util.*

@Entity
data class Account(
    @Id
    @GeneratedValue
    var accountId: UUID? = null,

    @Column(nullable = false)
    var fullName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    var password: String = "Mudar@123",

    var birthDate: Date? = null,

    @Column(nullable = false, updatable = false)
    var createdAt: Date = Date()
)
