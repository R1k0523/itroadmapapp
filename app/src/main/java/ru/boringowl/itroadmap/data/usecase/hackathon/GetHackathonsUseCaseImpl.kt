package ru.boringowl.itroadmap.data.usecase.hackathon

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.repository.HackathonRepository
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import ru.boringowl.itroadmap.domain.usecase.hackathon.GetHackathonsUseCase
import javax.inject.Inject

class GetHackathonsUseCaseImpl @Inject constructor(
    private val hackathonRepository: HackathonRepository,
): GetHackathonsUseCase {
    override suspend operator fun invoke(query: String): Flow<PagingData<Hackathon>> = hackathonRepository.get(query)
}