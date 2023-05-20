package ru.boringowl.itroadmap.domain.usecase.hackathon

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.domain.model.competence.Hackathon

interface GetHackathonsUseCase {
    suspend operator fun invoke(query: String): Flow<PagingData<Hackathon>>
}