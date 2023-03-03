package ru.cyclone.cycfinans.domain.repository

import ru.cyclone.cycfinans.data.local.dao.PromotionRepositoryImpl
import ru.cyclone.cycfinans.domain.model.Promotion
import javax.inject.Inject

class PromotionRepository @Inject constructor(private val promotionRepositoryImpl: PromotionRepositoryImpl) {

    suspend fun insertPromotion(promotion: Promotion) = promotionRepositoryImpl.insertPromotion(promotion = promotion)

    suspend fun getAllPromotion(): List<Promotion> = promotionRepositoryImpl.getAllPromotions()

    suspend fun updatePromotion(promotion: Promotion) = promotionRepositoryImpl.updatePromotion(promotion = promotion)

    suspend fun getPromotionById(promotionId: Long) = promotionRepositoryImpl.getPromotionById(promotionId = promotionId)

    suspend fun deletePromotion(promotion: Promotion) = promotionRepositoryImpl.deletePromotion(promotion = promotion)
}