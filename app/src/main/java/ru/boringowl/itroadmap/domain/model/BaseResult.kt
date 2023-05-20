package ru.boringowl.itroadmap.domain.model

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.ui.extensions.UIError
import ru.boringowl.itroadmap.ui.extensions.toUI

sealed class BaseResult<out T> {
    data class Success<T : Any>(val data: T) : BaseResult<T>()
    object Loading : BaseResult<Nothing>()
    data class Error(
        internal val message: String? = null,
        @StringRes internal val messageId: Int? = null,
    ) : BaseResult<Nothing>() {
        constructor(messageId: Int) : this(message = null, messageId = messageId)
    }


    fun <N : Any> cast(mapper: (T) -> N): BaseResult<N> {
        return when (this) {
            is Success -> Success(mapper(data))
            is Error -> Error(message, messageId)
            Loading -> Loading
        }
    }
}
typealias FlowResult<T> = Flow<BaseResult<T>>


suspend fun <T: Any> BaseResult<T>.onSuccess(block: suspend (T) -> Unit): BaseResult<T> {
    if(this is BaseResult.Success) block(this.data)
    return this
}
suspend fun <T: Any> BaseResult<T>.onLoading(block: suspend () -> Unit): BaseResult<T> {
    if(this is BaseResult.Loading) block()
    return this
}
suspend fun <T: Any> BaseResult<T>.onError(block: suspend (UIError) -> Unit): BaseResult<T> {
    if(this is BaseResult.Error) block(this.toUI())
    return this
}