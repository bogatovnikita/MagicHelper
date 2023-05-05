import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_cases.DeleteUseCaseImpl
import file_manager.doman.overview.use_cases.UpdateUseCase
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

    private val deleteUseCase = DeleteUseCaseImpl(
        deleter = deleter,
        server = server,
        updateUseCase = updateUseCase,
        uiOuter = uiOuter
    )


    @Test
    fun testDeleteAndUpdate(){
        val ids = emptyList<String>()

        coEvery { server.selected } returns ids

        deleteUseCase.deleteAndUpdate()

        coVerifySequence {
            uiOuter.showDeleteProgress()
            deleter.delete(ids)
            updateUseCase.update()
            uiOuter.showDeleteCompletion()
        }
    }


}