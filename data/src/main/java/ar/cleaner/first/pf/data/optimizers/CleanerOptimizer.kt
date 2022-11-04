package ar.cleaner.first.pf.data.optimizers

import android.app.Application
import android.net.Uri
import android.provider.DocumentsContract
import androidx.documentfile.provider.DocumentFile
import ar.cleaner.first.pf.data.managers.CleanerManager
import ar.cleaner.first.pf.data.optimizers.base.BaseOptimizer
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.exception.addToCollection
import ar.cleaner.first.pf.domain.mapper.asPercents
import ar.cleaner.first.pf.domain.models.App
import ar.cleaner.first.pf.domain.models.Junk
import ar.cleaner.first.pf.domain.models.JunkFile
import ar.cleaner.first.pf.domain.utils.emitProgressWithDelay
import ar.cleaner.first.pf.domain.utils.emulateProgressWorkFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class CleanerOptimizer @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val cleanerManager: CleanerManager,
    private val context: Application
) : BaseOptimizer() {
    override val lastOptimizationWorkMillis: StateFlow<Long>
        get() = preferencesManager.cleanerOptimizationFlow

    private val packageUriList by lazy {
        if (preferencesManager.dataFolderUri.isNotBlank()) {
            val treeUri = Uri.parse(preferencesManager.dataFolderUri)
            val tree = DocumentFile.fromTreeUri(context, treeUri)
            tree.listFiles()
        } else emptyList()
    }

    override fun setLastOptimizationTime(time: Long) {
        preferencesManager.cleanerLastOptimizationMillis = time
    }

    fun emulateOptimization(): Flow<Int> = emulateProgressWorkFlow()

    fun runOptimization(files: List<Junk>): Flow<Int> {
        return flow {
            val calculatedJunkSize = files.sumOf { it.size }
            files.forEachIndexed { index, junk ->
                val filePos = index + 1
                when (junk) {
                    is JunkFile -> {
                        deleteFile(junk)
                    }
                    is App -> {
                        deleteFilesInAppFolders(junk.packageName)
                    }
                    else -> throw IllegalArgumentException("Unknown type!")
                }
                emit(filePos.asPercents(files.size))
            }
            preferencesManager.lastClearedJunk = calculatedJunkSize
        }
    }

    fun fastOptimization(): Flow<Int> = flow {
        val junkFiles = ArrayList<JunkFile>()
        emitProgressWithDelay(isRandomDelay = true, from = 0, to = 19)
        cleanerManager.getEmptyFolders().addToCollection(junkFiles)
        emitProgressWithDelay(isRandomDelay = true, from = 19, to = 39)
        cleanerManager.getThumbnails().addToCollection(junkFiles)
        emitProgressWithDelay(isRandomDelay = true, from = 39, to = 73)
        cleanerManager.getUnnecessaryApk().addToCollection(junkFiles)
        val calculatedJunkSize = junkFiles.sumOf { it.size }
        junkFiles.forEach { junkFile ->
            deleteFile(fileToDelete = junkFile)
        }
        emitProgressWithDelay(isRandomDelay = true, from = 73, to = 100)
        preferencesManager.lastClearedJunk = calculatedJunkSize
    }
    private fun deleteFilesInAppFolders(packageName: String) {
        packageUriList.forEach { uri ->
            if (packageName.trim() == uri.lastPathSegment?.substringAfterLast('/')?.trim()) {
                val subUri = DocumentsContract.buildDocumentUriUsingTree(
                    uri,
                    DocumentsContract.getDocumentId(uri)
                )
                val newTree = DocumentFile.fromTreeUri(context, subUri)
                newTree.listFiles().forEach { directoryUri ->
                    if (directoryUri.lastPathSegment?.contains("cache")!!) {
                        val file = DocumentFile.fromSingleUri(context, directoryUri)
                        file!!.delete()
                    }
                }
            }
        }
    }

    private fun DocumentFile?.listFiles(): List<Uri> {
        return if (this?.isDirectory!!) {
            listFiles().mapNotNull { file ->
                if (file.name != null) file.uri else null
            }
        } else {
            emptyList()
        }
    }

    private fun deleteFile(fileToDelete: JunkFile): Boolean {
        val file = File(fileToDelete.filePath)
        return file.deleteRecursively()
    }
}