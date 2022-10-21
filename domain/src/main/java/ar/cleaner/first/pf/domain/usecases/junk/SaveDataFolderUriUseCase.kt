package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository

class SaveDataFolderUriUseCase(private val junkUseCasRepository: JunkUseCasRepository) :
        (String) -> Unit {
    override fun invoke(uri: String) = junkUseCasRepository.provideDataFolderUri(uri)
}