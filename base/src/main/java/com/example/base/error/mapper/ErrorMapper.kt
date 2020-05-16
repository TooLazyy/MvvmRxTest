package com.example.base.error.mapper

interface ErrorMapper {

    fun mapError(error: Throwable): Throwable
}