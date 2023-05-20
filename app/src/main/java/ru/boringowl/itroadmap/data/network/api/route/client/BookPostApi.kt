package ru.boringowl.itroadmap.data.network.api.route.client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.boringowl.itroadmap.data.network.api.route.response.BookPostResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.data.network.base.PageResponse
import java.util.*

interface BookPostApi {
    @GET("${ConstantsServer.booksEndpoint}/route/{id}")
    suspend fun get(
        @Path("id") id: Int,
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("query") query: String = "",
    ): Response<PageResponse<BookPostResponse>>

    @GET("${ConstantsServer.booksEndpoint}/{id}")
    suspend fun get(
        @Path("id") id: UUID
    ): Response<BookPostResponse>
}