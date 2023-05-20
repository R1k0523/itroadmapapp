package ru.boringowl.itroadmap.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.boringowl.itroadmap.data.network.api.route.client.*
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.*
import ru.boringowl.itroadmap.data.persistence.room.db.LocalDatabase
import ru.boringowl.itroadmap.data.usecase.book.GetBooksUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.company.GetCompaniesUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.hackathon.GetHackathonsUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.route.GetRoutesUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.skill.GetSkillsUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.skilltodo.UpdateSkillTodoUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.todo.AddTodoUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.todo.DeleteTodoUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.todo.GetTodoSkillsUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.todo.GetTodosUseCaseImpl
import ru.boringowl.itroadmap.domain.usecase.book.GetBooksUseCase
import ru.boringowl.itroadmap.domain.usecase.company.GetCompaniesUseCase
import ru.boringowl.itroadmap.domain.usecase.hackathon.GetHackathonsUseCase
import ru.boringowl.itroadmap.domain.usecase.route.GetRoutesUseCase
import ru.boringowl.itroadmap.domain.usecase.skill.GetSkillsUseCase
import ru.boringowl.itroadmap.domain.usecase.skilltodo.GetTodoSkillsUseCase
import ru.boringowl.itroadmap.domain.usecase.skilltodo.UpdateSkillTodoUseCase
import ru.boringowl.itroadmap.domain.usecase.todo.AddTodoUseCase
import ru.boringowl.itroadmap.domain.usecase.todo.DeleteTodoUseCase
import ru.boringowl.itroadmap.domain.usecase.todo.GetTodosUseCase

@Module
@InstallIn(SingletonComponent::class)
interface RouteModule {

    @Binds
    fun getRoutesUseCase(useCase: GetRoutesUseCaseImpl): GetRoutesUseCase
    @Binds
    fun getHackathonsUseCase(useCase: GetHackathonsUseCaseImpl): GetHackathonsUseCase
    @Binds
    fun getBooksUseCase(useCase: GetBooksUseCaseImpl): GetBooksUseCase
    @Binds
    fun getSkillsUseCaseCase(useCase: GetSkillsUseCaseImpl): GetSkillsUseCase
    @Binds
    fun addTodoUseCase(useCase: AddTodoUseCaseImpl): AddTodoUseCase
    @Binds
    fun deleteTodoUseCase(useCase: DeleteTodoUseCaseImpl): DeleteTodoUseCase
    @Binds
    fun getTodosUseCase(useCase: GetTodosUseCaseImpl): GetTodosUseCase
    @Binds
    fun getTodoUseCase(useCase: GetTodoSkillsUseCaseImpl): GetTodoSkillsUseCase
    @Binds
    fun updateSkillTodoUseCase(useCase: UpdateSkillTodoUseCaseImpl): UpdateSkillTodoUseCase
    @Binds
    fun getCompaniesUseCase(useCase: GetCompaniesUseCaseImpl): GetCompaniesUseCase

    companion object {
        @Provides
        fun BookPostApi(@AuthClient client: Retrofit): BookPostApi = client.create(BookPostApi::class.java)
        @Provides
        fun HackApi(@AuthClient client: Retrofit): HackApi = client.create(HackApi::class.java)
        @Provides
        fun RouteApi(@AuthClient client: Retrofit): RouteApi = client.create(RouteApi::class.java)
        @Provides
        fun SkillApi(@AuthClient client: Retrofit): SkillApi = client.create(SkillApi::class.java)
        @Provides
        fun SkillTodoApi(@AuthClient client: Retrofit): SkillTodoApi = client.create(SkillTodoApi::class.java)
        @Provides
        fun TodoApi(@AuthClient client: Retrofit): TodoApi = client.create(TodoApi::class.java)
        @Provides
        fun CompanyApi(@AuthClient client: Retrofit): CompanyApi = client.create(CompanyApi::class.java)

        @Provides
        fun bookKeyLocalDataSource(db: LocalDatabase): BookKeyLocalDataSource = BookKeyLocalDataSource(db.bookKeyDao())
        @Provides
        fun bookPostLocalDataSource(db: LocalDatabase): BookPostLocalDataSource = BookPostLocalDataSource(db.bookPostDao())
        @Provides
        fun hackathonKeyLocalDataSource(db: LocalDatabase): HackathonKeyLocalDataSource = HackathonKeyLocalDataSource(db.hackathonKeyDao())
        @Provides
        fun hackathonLocalDataSource(db: LocalDatabase): HackathonLocalDataSource = HackathonLocalDataSource(db.hackathonDao())
        @Provides
        fun routeLocalDataSource(db: LocalDatabase): RouteLocalDataSource = RouteLocalDataSource(db.routeDao())
        @Provides
        fun skillLocalDataSource(db: LocalDatabase): SkillLocalDataSource = SkillLocalDataSource(db.skillDao())
        @Provides
        fun skillKeyLocalDataSource(db: LocalDatabase): SkillKeyLocalDataSource = SkillKeyLocalDataSource(db.skillKeyDao())
        @Provides
        fun skillTodoLocalDataSource(db: LocalDatabase): SkillTodoLocalDataSource = SkillTodoLocalDataSource(db.skillTodoDao())
        @Provides
        fun todoLocalDataSource(db: LocalDatabase): TodoLocalDataSource = TodoLocalDataSource(db.todoDao())
    }
}