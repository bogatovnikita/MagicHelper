package ar.cleaner.first.pf.domain.repositorys.junk

import ar.cleaner.first.pf.domain.models.Junk
import ar.cleaner.first.pf.domain.models.JunkFile
import ar.cleaner.first.pf.domain.repositorys.base.BaseRepository
import kotlinx.coroutines.flow.Flow

interface JunkUseCasRepository : BaseRepository {

    fun provideDataFolderUri(uri: String)

    fun fastOptimization(): Flow<Int>

    fun getLastClearedJunk(): Flow<Double>

    suspend fun getEmptyFolders(): List<JunkFile>

    suspend fun getUnnecessaryApk(): List<JunkFile>

    suspend fun getThumbnails(): List<JunkFile>

    suspend fun getEmulatedJunk(): Double

    suspend fun getTotalExternalMemory(): Double

    suspend fun getAvailableMemory(): Double

    fun getDataFolderUri(): Flow<String>

    fun startOptimization(files: List<Junk>): Flow<Int>

}