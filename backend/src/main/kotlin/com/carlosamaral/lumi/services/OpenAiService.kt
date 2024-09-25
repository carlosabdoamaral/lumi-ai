package com.carlosamaral.lumi.services

import com.carlosamaral.lumi.dto.GetAnswerResponseDTO
import com.carlosamaral.lumi.dto.MessageDTO
import com.carlosamaral.lumi.dto.OpenAiRequestDTO
import com.carlosamaral.lumi.dto.OpenAiResponseDTO
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OpenAiService(private val gson: Gson) {
    @Value("\${openai.max-tokens}")
    lateinit var maxTokens: String

    @Value("\${openai.n}")
    lateinit var n: String

    @Value("\${openai.model}")
    lateinit var model: String

    @Value("\${openai.url}")
    lateinit var url: String

    @Value("\${openai.api-key}")
    lateinit var apiKey: String

    fun getAnswer(messages: List<MessageDTO>): GetAnswerResponseDTO {
        val openAiRequest = OpenAiRequestDTO(
            model,
            messages,
            n.toInt(),
            maxTokens.toInt()
        )

        val jsonMediaType = "application/json".toMediaType()
        val requestBody = gson.toJson(openAiRequest).toRequestBody(jsonMediaType)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        val openAiResponse = gson.fromJson(responseBody, OpenAiResponseDTO::class.java)
        response.close()

        val context: MutableList<MessageDTO> = messages.toMutableList()
        context.add(openAiResponse.choices[0].message)

        return GetAnswerResponseDTO(
            messages = context,
        )
    }
}