package ru.boringowl.itroadmap.data.network.base

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ResponseHandler @Inject constructor(val json: Json) {
    suspend fun <T> process(apiCall: suspend () -> Response<T>): T {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                return response.body()!!
            }
            val error = try {
                json.decodeFromString(response.errorBody()?.string().toString())
            } catch (e: Exception) {
                StringResponse("Нет сообщения")
            }
            throw when (response.code()) {
                HttpURLConnection.HTTP_BAD_REQUEST -> RequestException(
                    ErrorType.BAD_REQUEST,
                    errorText = error.message ?: ""
                )

                HttpURLConnection.HTTP_NOT_FOUND -> RequestException(
                    ErrorType.NOT_FOUND,
                    errorText = error.message ?: ""
                )

                HttpURLConnection.HTTP_INTERNAL_ERROR -> RequestException(ErrorType.SERVER)
                else -> RequestException(ErrorType.UNKNOWN)
            }
        } catch (exception: SocketTimeoutException) {
            throw RequestException(ErrorType.TIMEOUT)
        } catch (exception: UnknownHostException) {
            throw RequestException(ErrorType.CONNECTION)
        } catch (exception: IOException) {
            throw RequestException(ErrorType.UNKNOWN)
        }
    }
}