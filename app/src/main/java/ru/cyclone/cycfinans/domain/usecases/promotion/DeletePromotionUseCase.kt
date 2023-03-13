package ru.cyclone.cycfinans.domain.usecases.promotion

import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.repository.PromotionRepository
import javax.inject.Inject

class DeletePromotionUseCase @Inject constructor(private val promotionRepository: PromotionRepository) {
    suspend operator fun invoke(promotion: Promotion) = promotionRepository.deletePromotion(promotion = promotion)
}