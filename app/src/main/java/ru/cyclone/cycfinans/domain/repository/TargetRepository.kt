//package ru.cyclone.cycfinans.domain.repository
//
//import ru.cyclone.cycfinans.data.local.dao.TargeteRepositoryImpl
//import ru.cyclone.cycfinans.domain.model.Targete
//import javax.inject.Inject
//
//class TargeteRepository @Inject constructor(private val targetRepositoryImpl: TargeteRepositoryImpl) {
//    suspend fun insertTargete(targete: Targete) = targetRepositoryImpl.insertTarget(targete = targete)
//
//    suspend fun getAllTargets(): List<Targete> = targetRepositoryImpl.getAllTargetes()
//
//    suspend fun getTargeteById(id: Long) = targetRepositoryImpl.getTargetById(targeteId = id)
//
//    suspend fun deleteTargete(targete: Targete) = targetRepositoryImpl.deleteTargete(targete = targete)
//}