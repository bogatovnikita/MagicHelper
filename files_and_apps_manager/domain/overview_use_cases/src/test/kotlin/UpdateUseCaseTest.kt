import file_manager.doman.overview.OutCreator
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut
import file_manager.doman.overview.use_cases.UpdateUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class UpdateUseCaseTest {

    private val outCreator: OutCreator = mockk()
    private val uiOuter: UiOuter = spyk()
    private val useCase = UpdateUseCaseImpl(
        uiOuter = uiOuter,
        outCreator = outCreator
    )

    @Test
    fun testUpdate(){

        val updateOut = UpdateOut()
        coEvery { outCreator.createUpdateOut() } returns updateOut

        useCase.update()

        coVerify { uiOuter.out(updateOut) }

    }

}