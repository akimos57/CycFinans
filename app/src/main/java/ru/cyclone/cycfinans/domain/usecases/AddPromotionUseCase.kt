package ru.cyclone.cycfinans.domain.usecases

import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.repository.PromotionRepository
import javax.inject.Inject

class AddPromotionUseCase @Inject constructor(private val promotionRepository: PromotionRepository) {
    suspend operator fun invoke(promotion: Promotion) = promotionRepository.insertPromotion(promotion = promotion)
}