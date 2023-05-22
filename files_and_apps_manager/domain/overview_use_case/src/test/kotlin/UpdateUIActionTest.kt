import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut
import file_manager.doman.overview.use_case.UpdateUIActionImpl
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import yin_kio.file_app_manager.updater.Updater


@OptIn(ExperimentalCoroutinesApi::class)
class UpdateUIActionTest {

    private val outCreator: OutCreator = mockk()
    private val uiOuter: UiOuter = spyk()
    private val updater: Updater = spyk()

    private val useCase = UpdateUIActionImpl(
        uiOuter = uiOuter,
        outCreator = outCreator
    )

    @Test
    fun testUpdate() = runTest{

        val updateOut = UpdateOut()
        coEvery { outCreator.createUpdateOut() } returns updateOut

        useCase.update()

        coVerifyOrder{
            updater.update()
            uiOuter.out(updateOut)
        }

    }

}