package com.dm.berxley.ibank.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dm.berxley.ibank.core.data.local.entities.CurrencyEntity
import com.dm.berxley.ibank.core.data.local.entities.ExchangeRateEntity

@Database(entities = [CurrencyEntity::class, ExchangeRateEntity::class], version = 1)
abstract class BankDatabase : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        const val ROOM_DB_NAME = "bank_room_db"
    }
}