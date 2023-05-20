package ru.boringowl.itroadmap.data.usecase.book

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.repository.BookRepository
import ru.boringowl.itroadmap.domain.model.competence.BookPost
import ru.boringowl.itroadmap.domain.usecase.book.GetBooksUseCase
import javax.inject.Inject

class GetBooksUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository,
): GetBooksUseCase {
    override suspend operator fun invoke(query: String, routeId: Int): Flow<PagingData<BookPost>> = bookRepository.get(query, routeId)
}