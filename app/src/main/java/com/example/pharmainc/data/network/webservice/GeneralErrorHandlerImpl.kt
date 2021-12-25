package com.example.pharmainc.data.network.webservice

import com.example.core.domain.error.ErrorHandler
import com.example.core.domain.error.type.ErrorType

class GeneralErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorType {
        return ErrorType.Throwable
    }
}