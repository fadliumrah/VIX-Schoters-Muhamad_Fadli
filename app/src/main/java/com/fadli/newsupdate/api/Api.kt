package com.fadli.newsupdate.api

import com.fadli.newsupdate.util.Constant
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {
    companion object {
        const val BASE_URL = Constant.BASE_URL
        const val API_KEY = Constant.API_KEY

    }

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines?country=us&pageSize=100")
    suspend fun getBreakingNews(): Response


}