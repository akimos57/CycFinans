package ru.cyclone.cycfinans.data.local.dao

import androidx.room.*
import ru.cyclone.cycfinans.domain.model.Promotion

@Dao
interface PromotionRepositoryImpl {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotion(promotion: Promotion)

    @Query("SELECT * FROM promotion")
    suspend fun getAllPromotions(): List<Promotion>
    
    @Update
    suspend fun updatePromotion(promotion: Promotion)
    
    @Query("SELECT * FROM promotion WHERE id=:promotionId")
    suspend fun getPromotionById(promotionId: Long): Promotion

    @Delete
    suspend fun deletePromotion(promotion: Promotion)
}