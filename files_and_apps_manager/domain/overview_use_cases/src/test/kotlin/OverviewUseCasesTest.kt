import file_manager.doman.overview.OverviewUseCases
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut
import io.mockk.coVerify
import io.mockk.spyk
import org.junit.jupiter.api.Test

class OverviewUseCasesTest {

    private val uiOuter: UiOuter = spyk()
    private val useCases = OverviewUseCases(
        uiOuter = uiOuter
    )

    @Test
    fun testClose(){
        useCases.close()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testUpdate(){
        useCases.update()

        coVerify { uiOuter.out(UpdateOut()) }
    }

}