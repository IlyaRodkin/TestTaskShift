package by.rodkin.testtaskshift

import androidx.room.Database
import androidx.room.RoomDatabase
import by.rodkin.testtaskshift.data.StoreDAO
import by.rodkin.testtaskshift.data.model.BinInfoDB

@Database(entities = [BinInfoDB::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun storeDao(): StoreDAO
}