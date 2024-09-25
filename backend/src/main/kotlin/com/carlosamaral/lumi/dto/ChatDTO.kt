package com.carlosamaral.lumi.dto

data class GetAnswerRequestDTO(
    var messages: List<MessageDTO>,
)

data class GetAnswerResponseDTO(
    var messages: List<MessageDTO>,
)

data class MessageDTO(
    var role: String,
    var content: String,
)