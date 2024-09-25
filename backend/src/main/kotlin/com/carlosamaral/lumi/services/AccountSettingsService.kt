package com.carlosamaral.lumi.services

import com.carlosamaral.lumi.models.AccountSettings
import com.carlosamaral.lumi.repository.AccountSettingsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountSettingsService(private val accountSettingsRepository: AccountSettingsRepository) {

    fun findAll(): List<AccountSettings> = accountSettingsRepository.findAll()

    fun findById(accountSettingsId: UUID): AccountSettings? = accountSettingsRepository.findById(accountSettingsId).orElse(null)

    fun createAccountSettings(accountSettings: AccountSettings): AccountSettings = accountSettingsRepository.save(accountSettings)

    fun updateAccountSettings(accountSettingsId: UUID, updatedAccountSettings: AccountSettings): AccountSettings? {
        return accountSettingsRepository.findById(accountSettingsId).map { existingSettings ->
            val settingsToSave = existingSettings.copy(
                initialFormDone = updatedAccountSettings.initialFormDone,
                isFirstTime = updatedAccountSettings.isFirstTime
            )
            accountSettingsRepository.save(settingsToSave)
        }.orElse(null)
    }

    fun deleteAccountSettings(accountSettingsId: UUID) {
        accountSettingsRepository.deleteById(accountSettingsId)
    }
}
