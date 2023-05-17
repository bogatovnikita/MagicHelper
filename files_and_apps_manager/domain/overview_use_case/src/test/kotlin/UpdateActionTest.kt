import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut
import file_manager.doman.overview.use_case.UpdateActionImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class UpdateActionTest {

    private val outCreator: OutCreator = mockk()
    private val uiOuter: UiOuter = spyk()
    private val useCase = UpdateActionImpl(
        uiOuter = uiOuter,
        outCreator = outCreator
    )

    @Test
    fun testUpdate() = runTest{

        val updateOut = UpdateOut()
        coEvery { outCreator.createUpdateOut() } returns updateOut

        useCase.update()

        coVerify { uiOuter.out(updateOut) }

    }

}