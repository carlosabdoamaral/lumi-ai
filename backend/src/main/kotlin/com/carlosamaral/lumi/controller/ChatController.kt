package com.carlosamaral.lumi.controller

import com.carlosamaral.lumi.dto.GetAnswerRequestDTO
import com.carlosamaral.lumi.services.ChatService
import com.carlosamaral.lumi.services.OpenAiService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RestController
@RequestMapping("/ai")
class ChatController (
    private val openAiService: OpenAiService
){
    @CrossOrigin
    @PostMapping
    fun getAnswer(@RequestBody @Valid body: GetAnswerRequestDTO): ResponseEntity<Any> {
        val res = openAiService.getAnswer(body.messages)
        return ResponseEntity(res, HttpStatus.OK)
    }

}