package ru.cyclone.cycfinans.domain.usecases

import ru.cyclone.cycfinans.domain.repository.PromotionRepository
import javax.inject.Inject

class GetPromotionById @Inject constructor(private val promotionRepository: PromotionRepository){
    suspend operator fun invoke(promotionId: Long) = promotionRepository.getPromotionById(promotionId = promotionId)
}