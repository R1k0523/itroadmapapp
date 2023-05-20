package ru.boringowl.itroadmap.data.usecase.file

import ru.boringowl.itroadmap.data.network.api.user.request.PhotoRequest
import ru.boringowl.itroadmap.data.repository.FileRepository
import ru.boringowl.itroadmap.data.repository.UserRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.file.UploadProfileImageUseCase
import java.io.File
import javax.inject.Inject

class UploadProfileImageUseCaseImpl @Inject constructor(
    private val repo: FileRepository,
    private val userRepo: UserRepository
) : UploadProfileImageUseCase {
    override suspend operator fun invoke(file: File): BaseResult<String> {
        val uploadResult = repo.uploadFile(file)
        if (uploadResult !is BaseResult.Success)
            return uploadResult
        return userRepo.setPhoto(PhotoRequest(uploadResult.data)).cast { uploadResult.data }
    }
}