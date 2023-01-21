package by.rodkin.testtaskshift.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

@Serializable
data class BinInfo(
    val number: Number = Number(),
    val scheme: String = "",
    val type: String = "",
    val brand: String = "",
    val prepaid: Boolean? = null,
    val country: Country = Country(),
    val bank: Bank = Bank(),
)

@Entity
data class BinInfoDB(
    @PrimaryKey val id: Int,
    val json: String
)

fun BinInfo.mapToBinInfoDB() =
    BinInfoDB(Random.nextInt(), Json.encodeToString(this))

fun BinInfoDB.mapToBinInfo() =
    Json.decodeFromString<BinInfo>(this.json)

@Serializable
data class Number(
    val length: Int? = null,
    val luhn: Boolean? = null,
)

@Serializable
data class Country(
    val numeric: String = "",
    val alpha2: String = "",
    val name: String = "",
    val emoji: String = "",
    val currency: String = "",
    val latitude: Int? = null,
    val longitude: Int? = null,
)

@Serializable
data class Bank(
    val name: String = "",
    val url: String = "",
    val phone: String = "",
    val city: String = "",
)