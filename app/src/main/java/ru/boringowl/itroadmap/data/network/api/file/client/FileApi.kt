package ru.boringowl.itroadmap.data.network.api.file.client

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.boringowl.itroadmap.data.network.api.file.response.FileMetaResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer

interface FileApi {

    @Multipart
    @POST("/dev523/${ConstantsServer.fileEndpoint}")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    ): Response<FileMetaResponse>
}