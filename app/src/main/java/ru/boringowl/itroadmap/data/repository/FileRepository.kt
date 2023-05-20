package ru.boringowl.itroadmap.data.repository

import ru.boringowl.itroadmap.data.network.api.file.datasource.FileRemoteDataSource
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.domain.model.BaseResult
import java.io.File
import javax.inject.Inject

class FileRepository @Inject constructor(
    private val remoteDataSource: FileRemoteDataSource,
    private val handler: RequestHandler
) {
    suspend fun uploadFile(file: File): BaseResult<String> {
        return handler.execute { remoteDataSource.upload(file).id }
    }
}
