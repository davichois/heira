package com.heira.app.data.remote.dto

data class ResponseApi<T>(
    val body: T,
    val origin: String,
    val status: String,
    val url: String
)