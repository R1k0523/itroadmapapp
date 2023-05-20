package ru.boringowl.itroadmap.data.network.api.file.datasource

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.boringowl.itroadmap.data.network.api.file.client.FileApi
import ru.boringowl.itroadmap.data.network.api.file.response.toModel
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.file.FileMeta
import java.io.File
import javax.inject.Inject


class FileRemoteDataSource @Inject constructor(
    private val api: FileApi,
    private val handler: ResponseHandler,
) {
    suspend fun upload(file: File): FileMeta {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file", file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        return handler.process { api.uploadImage(filePart) }.toModel()
    }
}