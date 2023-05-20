package ru.boringowl.itroadmap.data.network.base

class RequestException(val type: ErrorType, val errorText: String = "") : Exception()

enum class ErrorType {
    NOT_FOUND, TIMEOUT, SERVER, CONNECTION, UNKNOWN, BAD_REQUEST
}