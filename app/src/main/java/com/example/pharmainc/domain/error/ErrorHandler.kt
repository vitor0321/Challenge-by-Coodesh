package com.example.pharmainc.domain.error

import com.example.pharmainc.domain.error.type.ErrorType

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorType
}