package com.example.pharmainc.domain.eventBus

import com.example.pharmainc.domain.model.ItemCheckGender

data class MessageEventGender(val message: ItemCheckGender)

data class MessageEventSearch(val message: String)
