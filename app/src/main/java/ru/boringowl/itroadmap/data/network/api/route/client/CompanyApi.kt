package ru.boringowl.itroadmap.data.network.api.route.client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.boringowl.itroadmap.data.network.api.route.response.CompanyResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.data.network.base.PageResponse
import java.util.*

interface CompanyApi {
    @GET(ConstantsServer.companyEndpoint)
    suspend fun get(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("query") query: String = "",
    ): Response<PageResponse<CompanyResponse>>

    @GET("${ConstantsServer.companyEndpoint}/{id}")
    suspend fun get(@Path("id") id: UUID): Response<CompanyResponse>
}