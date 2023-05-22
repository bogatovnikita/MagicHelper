import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteActionImpl
import file_manager.doman.overview.use_case.UpdateUIAction
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import yin_kio.file_app_manager.updater.Updater


@OptIn(ExperimentalCoroutinesApi::class)
class DeleteActionTest {

    private val deleter: Deleter = spyk()
    private val server: FileManagerServer = mockk()
    private val updateUIAction: UpdateUIAction = spyk()
    private val uiOuter: UiOuter = spyk()
    private val deleteTimeSaver: DeleteTimeSaver = spyk()
    private val updater: Updater = spyk()

    private val deleteUseCase = DeleteActionImpl(
        deleter = deleter,
        server = server,
        updateUIAction = updateUIAction,
        uiOuter = uiOuter,
        deleteTimeSaver = deleteTimeSaver,
        updater = updater
    )


    @Test
    fun testDeleteAndUpdate() = runTest{
        val video = listOf("video")
        val ids = listOf(FileOrApp(id = "video"))

        coEvery { server.getSelected(GroupName.Video) } returns ids

        deleteUseCase.deleteAndUpdate(GroupName.Video)

        coVerifySequence {
            uiOuter.showDeleteProgress()

            deleter.delete(video.map { it })
            deleteTimeSaver.saveDeleteTime()
            updater.update()
            updateUIAction.update()

            uiOuter.showDeleteCompletion()
        }
    }


}