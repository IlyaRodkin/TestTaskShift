package by.rodkin.testtaskshift.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.rodkin.testtaskshift.data.model.BinInfoDB
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDAO {

    @Query("SELECT * FROM bininfodb")
    fun getAll(): Flow<List<BinInfoDB>>

    @Insert
    fun insert(vararg data: BinInfoDB)
}