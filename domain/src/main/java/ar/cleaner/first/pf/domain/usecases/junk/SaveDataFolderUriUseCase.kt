package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import javax.inject.Inject

class SaveDataFolderUriUseCase @Inject constructor (private val junkUseCasRepository: JunkUseCasRepository) :
        (String) -> Unit {
    override fun invoke(uri: String) = junkUseCasRepository.provideDataFolderUri(uri)
}