package com.cms.common.exception

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExceptionMessage(
    val code: Int,
    val status: HttpStatus,
    val errorMessage: String?,
    val detail: String?
) {
    constructor(code: HttpStatus, message: String?, detail: String?) : this(
        code.value(),
        code,
        message,
        detail
    )
}
