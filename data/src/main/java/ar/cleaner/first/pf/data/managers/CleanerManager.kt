package ar.cleaner.first.pf.data.managers

import android.app.Application
import android.os.Environment
import android.os.StatFs
import ar.cleaner.first.pf.data.extensions.asJunkFile
import ar.cleaner.first.pf.data.managers.CleanerManager.SearchJunk.searchRecursivelyEmptyFolders
import ar.cleaner.first.pf.data.managers.CleanerManager.SearchJunk.searchRecursivelyThumbnails
import ar.cleaner.first.pf.data.managers.CleanerManager.SearchJunk.searchRecursivelyUnnecessaryApk
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.const.Const
import ar.cleaner.first.pf.domain.const.Const.DEFAULT_DOUBLE_VALUE
import ar.cleaner.first.pf.domain.models.JunkFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

class CleanerManager @Inject constructor(
    private val context: Application,
    private val preferencesManager: PreferencesManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private fun externalMemoryAvailable(): Boolean {
        return Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED
    }

    fun getLastClearedJunk(): Flow<Double> = preferencesManager.lastClearedJunkFlow

    suspend fun generateRandomGbJunk(): Double = withContext(ioDispatcher) {
        val junk = Random.nextDouble(1024 * 1024 * 512.0, 1024 * 1024 * 1024 * 2.0)
        preferencesManager.lastClearedJunk = junk
        junk
    }

    fun saveDataUriPath(uri: String) {
        preferencesManager.dataFolderUri = uri
    }

    suspend fun getTotalExternalMemorySize(): Double = withContext(ioDispatcher) {
        if (externalMemoryAvailable()) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong

            ((totalBlocks * blockSize).toDouble() / (Const.GB))
        } else DEFAULT_DOUBLE_VALUE
    }

    suspend fun getAvailableMemorySize(): Double = withContext(ioDispatcher) {
        if (externalMemoryAvailable()) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            ((availableBlocks * blockSize).toDouble() / (Const.GB))
        } else DEFAULT_DOUBLE_VALUE
    }

    suspend fun getThumbnails(): List<JunkFile> = withContext(ioDispatcher) {
        val parentDir = Environment.getExternalStorageDirectory()
        val emptyDirectories = ArrayList<JunkFile>()
        searchRecursivelyThumbnails(parentDir) {
            emptyDirectories.add(it.asJunkFile(context))
        }
        emptyDirectories
    }

    suspend fun getEmptyFolders(): List<JunkFile> = withContext(ioDispatcher) {
        val parentDir = Environment.getExternalStorageDirectory()
        val emptyDirectories = ArrayList<JunkFile>()
        searchRecursivelyEmptyFolders(parentDir) {
            emptyDirectories.add(it.asJunkFile(context))
        }
        emptyDirectories
    }

    suspend fun getUnnecessaryApk(): List<JunkFile> = withContext(ioDispatcher) {
        val parentDir = Environment.getExternalStorageDirectory()
        val apkFiles = ArrayList<JunkFile>()
        searchRecursivelyUnnecessaryApk(parentDir) {
            apkFiles.add(it.asJunkFile(context))
        }
        apkFiles
    }

    private object SearchJunk {
        private const val THUMBNAILS_FOLDER: String = ".thumbnails"
        private const val APK_FOLDER: String = "apk"
        private const val ANDROID_FOLDER: String = "Android"

        private val androidDirectory =
            File(Environment.getExternalStorageDirectory(), ANDROID_FOLDER)

        fun searchRecursivelyEmptyFolders(fileOrDirectory: File, callback: (File) -> Unit) {
            if (fileOrDirectory.isDirectory && fileOrDirectory != androidDirectory) {
                if (!fileOrDirectory.listFiles().isNullOrEmpty()) {
                    fileOrDirectory.listFiles()!!.forEach { file ->
                        if (file.isDirectory) {
                            searchRecursivelyEmptyFolders(file, callback)
                        }
                    }
                } else callback(fileOrDirectory)
            }
        }

        fun searchRecursivelyUnnecessaryApk(file: File, callback: (File) -> Unit) {
            if (!file.isDirectory && file.extension == APK_FOLDER && file != androidDirectory) {
                callback(file)
            } else {
                if (!file.listFiles().isNullOrEmpty()) {
                    file.listFiles()!!.forEach {
                        searchRecursivelyUnnecessaryApk(it, callback)
                    }
                }
            }
        }

        fun searchRecursivelyThumbnails(file: File, callback: (File) -> Unit) {
            if (file.isDirectory && file.name == THUMBNAILS_FOLDER && file != androidDirectory) {
                callback(file)
            } else {
                if (!file.listFiles().isNullOrEmpty()) {
                    file.listFiles()!!.forEach {
                        searchRecursivelyThumbnails(it, callback)
                    }
                }
            }
        }
    }
}