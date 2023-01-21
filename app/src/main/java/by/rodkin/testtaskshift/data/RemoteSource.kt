package by.rodkin.testtaskshift.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteSource @Inject constructor(
    val config: Retrofit
) {
    private val catalogApi = config.create(RemoteApi::class.java)

    suspend fun getInfoFromBin(bin: String) =
        withContext(Dispatchers.IO) { catalogApi.fetchInfoFromBin(bin) }
}