import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteActionImpl
import file_manager.doman.overview.use_case.UpdateAction
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class DeleteActionTest {

    private val deleter: Deleter = spyk()
    private val server: FileManagerServer = mockk()
    private val updateAction: UpdateAction = spyk()
    private val uiOuter: UiOuter = spyk()
    private val deleteTimeSaver: DeleteTimeSaver = spyk()

    private val deleteUseCase = DeleteActionImpl(
        deleter = deleter,
        server = server,
        updateAction = updateAction,
        uiOuter = uiOuter,
        deleteTimeSaver = deleteTimeSaver
    )


    @Test
    fun testDeleteAndUpdate(){
        val video = listOf("video")
        val ids = listOf(FileOrApp(id = "video"))

        coEvery { server.getSelected(GroupName.Video) } returns ids

        deleteUseCase.deleteAndUpdate(GroupName.Video)

        coVerifySequence {
            uiOuter.showDeleteProgress()

            deleter.delete(video.map { it })
            deleteTimeSaver.saveDeleteTime()
            updateAction.update()

            uiOuter.showDeleteCompletion()
        }
    }


}