package com.carlosamaral.lumi.repository

import com.carlosamaral.lumi.models.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository : JpaRepository<Chat, UUID>
