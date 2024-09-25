package com.carlosamaral.lumi.services

import com.carlosamaral.lumi.models.Account
import com.carlosamaral.lumi.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun findAll(): List<Account> = accountRepository.findAll()

    fun findById(accountId: UUID): Account? = accountRepository.findById(accountId).orElse(null)

    fun createAccount(account: Account): Account = accountRepository.save(account)

    fun updateAccount(accountId: UUID, updatedAccount: Account): Account? {
        return accountRepository.findById(accountId).map { existingAccount ->
            val accountToSave = existingAccount.copy(
                fullName = updatedAccount.fullName,
                email = updatedAccount.email,
                password = updatedAccount.password,
                birthDate = updatedAccount.birthDate
            )
            accountRepository.save(accountToSave)
        }.orElse(null)
    }

    fun deleteAccount(accountId: UUID) {
        accountRepository.deleteById(accountId)
    }
}
