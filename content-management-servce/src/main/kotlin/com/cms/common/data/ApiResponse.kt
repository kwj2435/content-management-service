package com.cms.common.data

import org.springframework.http.HttpStatus

class ApiResponse<T>(
    val code: Int,
    val status: HttpStatus,
    val result: T
) {
    constructor(result: T): this(
        HttpStatus.OK.value(), HttpStatus.OK, result
    )
}
