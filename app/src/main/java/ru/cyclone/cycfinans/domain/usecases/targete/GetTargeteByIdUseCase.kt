//package ru.cyclone.cycfinans.domain.usecases.targete
//
//import ru.cyclone.cycfinans.domain.repository.TargeteRepository
//import javax.inject.Inject
//
//class GetTargeteByIdUseCase @Inject constructor(private val targeteRepository: TargeteRepository) {
//    suspend operator fun invoke(id: Long) = targeteRepository.getTargeteById(id= id)
//}