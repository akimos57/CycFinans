//package ru.cyclone.cycfinans.data.local.dao
//
//import androidx.room.*
//import ru.cyclone.cycfinans.domain.model.Targete
//
//@Dao
//interface TargeteRepositoryImpl {
//    @Insert
//    suspend fun insertTarget(targete: Targete)
//
//    @Query("SELECT * FROM targete")
//    suspend fun getAllTargetes(): List<Targete>
//
//    @Update
//    suspend fun updateTarget(targete: Targete)
//
//    @Query("SELECT * FROM targete WHERE id=:targeteId")
//    suspend fun getTargetById(targeteId: Long)
//
//    @Delete
//    suspend fun deleteTargete(targete: Targete)
//}