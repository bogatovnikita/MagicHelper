package file_manager.doman.overview.use_cases

import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.UiOuter

class UpdateUseCaseImpl(
    private val uiOuter: UiOuter,
    private val outCreator: OutCreator
) : UpdateUseCase {

    override fun update() {
        uiOuter.out(outCreator.createUpdateOut())
    }
}