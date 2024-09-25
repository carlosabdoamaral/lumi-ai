package com.carlosamaral.lumi.dto

data class OpenAiRequestDTO(
    var model: String,
    var messages: List<MessageDTO>,
    var n: Int,
    var max_tokens: Int
)

data class OpenAiResponseDTO(
    val id: String,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage?,
)

data class Choice(
    val index: Int,
    val message: MessageDTO,
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)
