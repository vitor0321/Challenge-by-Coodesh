package com.example.core.domain.error

import com.example.core.domain.error.type.ErrorType

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorType
}