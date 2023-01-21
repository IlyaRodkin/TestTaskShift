package by.rodkin.testtaskshift.data

import by.rodkin.testtaskshift.data.model.BinInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("/{bin}")
    suspend fun fetchInfoFromBin(@Path("bin") bin: String): BinInfo
}