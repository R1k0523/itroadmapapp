package ru.boringowl.itroadmap.domain.usecase.file

import ru.boringowl.itroadmap.domain.model.BaseResult
import java.io.File

interface UploadProfileImageUseCase {
    suspend operator fun invoke(file: File): BaseResult<String>
}