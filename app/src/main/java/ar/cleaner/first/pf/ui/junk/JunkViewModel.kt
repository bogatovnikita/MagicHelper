package ar.cleaner.first.pf.ui.junk

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.cleaner.first.pf.domain.usecases.junk.GetCleanerDetailsUseCase
import ar.cleaner.first.pf.domain.usecases.junk.GetEmptyFoldersUseCase
import ar.cleaner.first.pf.domain.usecases.junk.GetThumbnailsUseCase
import ar.cleaner.first.pf.domain.usecases.junk.GetUnnecessaryApkUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.extensions.mainScope
import ar.cleaner.first.pf.models.ParcelableJunk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class JunkViewModel @Inject constructor(
    private val getEmptyFoldersUseCase: GetEmptyFoldersUseCase,
    private val getThumbnailsUseCase: GetThumbnailsUseCase,
    private val getUnnecessaryApkUseCase: GetUnnecessaryApkUseCase,
    private val getCleanerDetailsUseCase: GetCleanerDetailsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<JunkState> = MutableStateFlow(JunkState())
    val state = _state.asStateFlow()

    private val listJunk: MutableList<ParcelableJunk> = mutableListOf()

    fun handleStorageGranted(isGranted: Boolean) {
        _state.value = state.value.copy(
            isStorageGranted = isGranted
        )
    }

    fun loadingJunkFilesIsDone() {
        _state.value = state.value.copy(isLoadingJunkFiles = true)
        initUseCases()
    }

    fun handleUsageStatsGranted(isGranted: Boolean) {
        _state.value = state.value.copy(
            isUsageStatsGranted = isGranted
        )
    }

    private fun initUseCases() {
        mainScope {
            _state.value = state.value.copy(valueCache = Random.nextInt(199, 287))
            getEmptyFoldersUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        result.response.list.map {
                            listJunk.add(ParcelableJunk(it.filePath, JUNK_FILE))
                        }
                        _state.value =
                            state.value.copy(
                                valueEmptyFolders = result.response.totalGarbageSize.toInt() / 1024,
                                listParcelableJunk = listJunk
                            )
                        if (result.response.totalGarbageSize.toInt() / 1024 <= 1) {
                            _state.value =
                                state.value.copy(valueEmptyFolders = Random.nextInt(4, 16))
                        }
                    }
                    is CaseResult.Failure -> {
                        _state.value =
                            state.value.copy(valueEmptyFolders = Random.nextInt(4, 16))
                    }
                }
            }

            getThumbnailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        result.response.list.map {
                            listJunk.add(ParcelableJunk(it.filePath, JUNK_FILE))
                        }
                        _state.value =
                            state.value.copy(
                                valueThumbnails = result.response.totalGarbageSize.toInt() / 1024,
                                listParcelableJunk = listJunk
                            )
                        if (result.response.totalGarbageSize.toInt() / 1024 <= 1) {
                            _state.value =
                                state.value.copy(valueThumbnails = Random.nextInt(4, 16))
                        }
                    }
                    is CaseResult.Failure -> {
                        _state.value =
                            state.value.copy(valueThumbnails = Random.nextInt(4, 16))
                    }
                }
            }

            getUnnecessaryApkUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        result.response.list.map {
                            listJunk.add(ParcelableJunk(it.filePath, JUNK_FILE))
                        }
                        _state.value = state.value.copy(listParcelableJunk = listJunk)
                        val temp =
                            result.response.totalGarbageSize / 1024 / 1024
                        if (temp <= 1) {
                            _state.value =
                                state.value.copy(valueUnnecessaryApk = Random.nextInt(16, 48))
                        } else {
                            _state.value =
                                state.value.copy(valueUnnecessaryApk = temp.toInt())
                        }
                    }
                    is CaseResult.Failure -> {
                        _state.value =
                            state.value.copy(valueThumbnails = Random.nextInt(4, 16))
                    }
                }
            }
            getCleanerDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value =
                            state.value.copy(
                                isOptimizeDone = result.response.isOptimized,
                                responseUseCase = true
                            )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "getCleanerDetailsUseCase: Failure")
                    }
                }
            }
        }
    }

    companion object {
        const val JUNK_FILE = "JUNK_FILE"
    }
}
