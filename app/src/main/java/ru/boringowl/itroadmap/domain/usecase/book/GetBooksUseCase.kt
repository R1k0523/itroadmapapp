package ru.boringowl.itroadmap.domain.usecase.book

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.domain.model.competence.BookPost

interface GetBooksUseCase {
    suspend operator fun invoke(query: String, routeId: Int): Flow<PagingData<BookPost>>
}