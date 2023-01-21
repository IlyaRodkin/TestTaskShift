package by.rodkin.testtaskshift.domain

import by.rodkin.testtaskshift.Database
import by.rodkin.testtaskshift.data.RemoteSource
import by.rodkin.testtaskshift.data.model.BinInfoDB
import by.rodkin.testtaskshift.data.model.mapToBinInfo
import by.rodkin.testtaskshift.data.model.mapToBinInfoDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BinRepo @Inject constructor(
    val source: RemoteSource,
    val db: Database
) {
    val dbBin = db.storeDao()
    suspend fun getInfoFromBinNetwork(bin: String) =
        source.getInfoFromBin(bin).also { insertBin(it.mapToBinInfoDB()) }

    suspend fun getHistory() =
        withContext(Dispatchers.IO) { dbBin.getAll().map { it.map { it.mapToBinInfo() } } }

    private suspend fun insertBin(data: BinInfoDB) = withContext(Dispatchers.IO) { dbBin.insert(data) }
}