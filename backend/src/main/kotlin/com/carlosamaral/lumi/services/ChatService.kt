package com.carlosamaral.lumi.services

import com.carlosamaral.lumi.models.Chat
import com.carlosamaral.lumi.repository.ChatRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(private val chatRepository: ChatRepository) {

    fun findAll(): List<Chat> = chatRepository.findAll()

    fun findById(chatId: UUID): Chat? = chatRepository.findById(chatId).orElse(null)

    fun createChat(chat: Chat): Chat = chatRepository.save(chat)

    fun updateChat(chatId: UUID, updatedChat: Chat): Chat? {
        return chatRepository.findById(chatId).map { existingChat ->
            val chatToSave = existingChat.copy(
                accountId = updatedChat.accountId,
                title = updatedChat.title
            )
            chatRepository.save(chatToSave)
        }.orElse(null)
    }

    fun deleteChat(chatId: UUID) {
        chatRepository.deleteById(chatId)
    }
}
