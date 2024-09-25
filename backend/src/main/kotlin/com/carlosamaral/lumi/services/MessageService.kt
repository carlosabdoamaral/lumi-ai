package com.carlosamaral.lumi.services

import com.carlosamaral.lumi.models.Message
import com.carlosamaral.lumi.repository.MessageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun findAll(): List<Message> = messageRepository.findAll()

    fun findById(messageId: UUID): Message? = messageRepository.findById(messageId).orElse(null)

    fun createMessage(message: Message): Message = messageRepository.save(message)

    fun updateMessage(messageId: UUID, updatedMessage: Message): Message? {
        return messageRepository.findById(messageId).map { existingMessage ->
            val messageToSave = existingMessage.copy(
                chatId = updatedMessage.chatId,
                text = updatedMessage.text,
                negative = updatedMessage.negative,
                neutral = updatedMessage.neutral,
                positive = updatedMessage.positive,
                compound = updatedMessage.compound,
                promptTokens = updatedMessage.promptTokens,
                completionTokens = updatedMessage.completionTokens,
                totalTokens = updatedMessage.totalTokens
            )
            messageRepository.save(messageToSave)
        }.orElse(null)
    }

    fun deleteMessage(messageId: UUID) {
        messageRepository.deleteById(messageId)
    }
}
