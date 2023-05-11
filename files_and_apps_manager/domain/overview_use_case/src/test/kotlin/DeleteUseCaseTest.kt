import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteUseCaseImpl
import file_manager.doman.overview.use_case.UpdateUseCase
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class DeleteUseCaseTest {

    private val deleter: Deleter = spyk()
    private val server: FileManagerServer = mockk()
    private val updateUseCase: UpdateUseCase = spyk()
    private val uiOuter: UiOuter = spyk()
    private val deleteTimeSaver: DeleteTimeSaver = spyk()

    private val deleteUseCase = DeleteUseCaseImpl(
        deleter = deleter,
        server = server,
        updateUseCase = updateUseCase,
        uiOuter = uiOuter,
        deleteTimeSaver = deleteTimeSaver
    )


    @Test
    fun testDeleteAndUpdate(){
        val video = listOf("video")
        val ids = listOf("video")

        coEvery { server.getSelected(GroupName.Video) } returns ids

        deleteUseCase.deleteAndUpdate(GroupName.Video)

        coVerifySequence {
            uiOuter.showDeleteProgress()

            deleter.delete(video.map { it })
            deleteTimeSaver.saveDeleteTime()
            updateUseCase.update()

            uiOuter.showDeleteCompletion()
        }
    }


}