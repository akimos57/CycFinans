//package ru.cyclone.cycfinans.domain.usecases.targete
//
//import ru.cyclone.cycfinans.domain.model.Targete
//import ru.cyclone.cycfinans.domain.repository.TargeteRepository
//import javax.inject.Inject
//
//class DeleteTargeteUseCase @Inject constructor(private val targeteRepository: TargeteRepository) {
//    suspend operator fun invoke(targete: Targete) = targeteRepository.insertTargete(targete = targete)
//}