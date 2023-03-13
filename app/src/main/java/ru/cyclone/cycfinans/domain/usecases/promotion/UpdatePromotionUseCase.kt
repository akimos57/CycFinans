package ru.cyclone.cycfinans.domain.usecases.promotion

import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.repository.PromotionRepository
import javax.inject.Inject

class UpdatePromotionUseCase @Inject constructor(private val promotionRepository: PromotionRepository) {
    suspend operator fun invoke(promotion: Promotion) = promotionRepository.updatePromotion(promotion = promotion)
}