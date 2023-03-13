package ru.cyclone.cycfinans.domain.usecases.promotion

import ru.cyclone.cycfinans.domain.repository.PromotionRepository
import javax.inject.Inject

class GetAllPromotionUseCase @Inject constructor(private val promotionRepository: PromotionRepository) {
    suspend operator fun invoke() = promotionRepository.getAllPromotion()
}