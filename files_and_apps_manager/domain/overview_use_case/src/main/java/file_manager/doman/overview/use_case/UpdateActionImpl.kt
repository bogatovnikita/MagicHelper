package file_manager.doman.overview.use_case

import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.UiOuter

class UpdateActionImpl(
    private val uiOuter: UiOuter,
    private val outCreator: OutCreator,

) : UpdateAction {

    override suspend fun update() {


        uiOuter.out(outCreator.createUpdateOut())
    }
}