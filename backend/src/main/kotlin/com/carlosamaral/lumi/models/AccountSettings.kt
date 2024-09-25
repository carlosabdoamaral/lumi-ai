package com.carlosamaral.lumi.models

import jakarta.persistence.*
import java.util.*

@Entity
data class AccountSettings(
    @Id
    @GeneratedValue
    var accountSettingsId: UUID? = null,

    var initialFormDone: Boolean = false,

    var isFirstTime: Boolean = true
)
