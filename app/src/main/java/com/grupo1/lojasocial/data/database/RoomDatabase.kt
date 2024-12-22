package com.grupo1.lojasocial.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grupo1.lojasocial.data.model.BeneficiaryDao
import com.grupo1.lojasocial.domain.model.Beneficiary

@Database(entities = [Beneficiary::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun beneficiaryDao(): BeneficiaryDao
}
