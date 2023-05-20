package ru.boringowl.itroadmap.data.network.base

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.BaseResult
import java.lang.Exception
import javax.inject.Inject

class RequestHandler @Inject constructor() {
    val TAG = this::class.simpleName

    suspend fun <Response : Any> execute(
        onBefore: suspend () -> Unit = {},
        onAfter: suspend () -> Unit = {},
        onConnectionError: suspend () -> Unit = {},
        processRequest: suspend () -> Response,
    ): BaseResult<Response> {
        return try {
            onBefore()
            val result = processRequest()
            onAfter()
            BaseResult.Success(result)
        } catch (exception: RequestException) {
            when (exception.type) {
                ErrorType.BAD_REQUEST, ErrorType.NOT_FOUND -> {
                    BaseResult.Error(message = exception.errorText)
                }

                ErrorType.UNKNOWN, ErrorType.TIMEOUT -> {
                    BaseResult.Error(messageId = R.string.error_unknown)
                }

                ErrorType.SERVER, ErrorType.CONNECTION -> {
                    onConnectionError()
                    BaseResult.Error(messageId = R.string.error_network)
                }
            }
        }
    }

    fun <Response : Any, Request> execute(
        processRequest: suspend () -> Request,
        onAfter: suspend () -> Flow<Response?>,
        doSync: suspend (Request) -> Unit,
        remote: Boolean = true,
    ): Flow<BaseResult<Response>> {
        return flow {
            if (remote) {
                emit(BaseResult.Loading)
                try {
                    val result = processRequest()
                    doSync(result)
                } catch (exception: RequestException) {
                    Log.e(TAG, exception.message.toString())
                }
            }
            val result = onAfter().map {
                if (it != null)
                    BaseResult.Success(it)
                else
                    BaseResult.Error(R.string.error_not_found)
            }
            emitAll(result)
        }
    }

    fun <Response : Any, Request> execute(
        processRequest: suspend () -> Request,
        onResultSaved: suspend () -> Flow<Response?>,
        onAfter: suspend (Request) -> Unit,
        onError: suspend (Exception) -> Unit = {},
    ): Flow<BaseResult<Response>> {
        return flow {
            emit(BaseResult.Loading)
            try {
                val result = processRequest()
                onAfter(result)
            } catch (exception: RequestException) {
                Log.e(TAG, exception.message.toString())
                onError(exception)
            }
            val result = onResultSaved().map {
                if (it != null)
                    BaseResult.Success(it)
                else
                    BaseResult.Error(messageId = R.string.error_unknown)
            }
            emitAll(result)
        }
    }
}

