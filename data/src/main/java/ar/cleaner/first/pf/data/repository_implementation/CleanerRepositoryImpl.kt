package ar.cleaner.first.pf.data.repository_implementation

import ar.cleaner.first.pf.data.managers.CleanerManager
import ar.cleaner.first.pf.data.optimizers.CleanerOptimizer
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.models.Junk
import ar.cleaner.first.pf.domain.models.JunkFile
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CleanerRepositoryImpl @Inject constructor(
    private val cleanerOptimizer: CleanerOptimizer,
    private val cleanerManager: CleanerManager,
    private val preferencesManager: PreferencesManager
) : JunkUseCasRepository {
    override fun provideDataFolderUri(uri: String) = cleanerManager.saveDataUriPath(uri)

    override fun fastOptimization(): Flow<Int> = cleanerOptimizer.fastOptimization()

    override fun getLastClearedJunk(): Flow<Double> = cleanerManager.getLastClearedJunk()

    override suspend fun getEmptyFolders(): List<JunkFile> = cleanerManager.getEmptyFolders()

    override suspend fun getUnnecessaryApk(): List<JunkFile> = cleanerManager.getUnnecessaryApk()

    override suspend fun getThumbnails(): List<JunkFile> = cleanerManager.getThumbnails()

    override suspend fun getEmulatedJunk(): Double = cleanerManager.generateRandomGbJunk()

    override suspend fun getTotalExternalMemory(): Double =
        cleanerManager.getTotalExternalMemorySize()

    override suspend fun getAvailableMemory(): Double =
        cleanerManager.getAvailableMemorySize()

    override fun getDataFolderUri(): Flow<String> = preferencesManager.dataFolderUriFlow

    override fun startOptimization(files: List<Junk>): Flow<Int> =
        cleanerOptimizer.runOptimization(files)


    override val lastOptimizationMillis: StateFlow<Long>
        get() = cleanerOptimizer.lastOptimizationWorkMillis

    override fun setLastOptimizationMillis(time: Long) =
        cleanerOptimizer.setLastOptimizationTime(time)

    override fun emulateOptimization(): Flow<Int> = cleanerOptimizer.emulateOptimization()
}