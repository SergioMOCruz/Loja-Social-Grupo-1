package com.grupo1.lojasocial.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.grupo1.lojasocial.domain.model.Beneficiary
import kotlinx.coroutines.flow.Flow

@Dao
interface BeneficiaryDao {

    @Insert
    suspend fun insert(beneficiary: Beneficiary)

    @Update
    suspend fun update(beneficiary: Beneficiary)

    @Delete
    suspend fun delete(beneficiary: Beneficiary)

    @Query("SELECT * FROM beneficiaries ORDER BY createdAt DESC LIMIT 10")
    fun getAllBeneficiaries(): Flow<List<Beneficiary>>

    @Query("SELECT * FROM beneficiaries WHERE id = :id")
    suspend fun getBeneficiaryById(id: String): Beneficiary?
}
