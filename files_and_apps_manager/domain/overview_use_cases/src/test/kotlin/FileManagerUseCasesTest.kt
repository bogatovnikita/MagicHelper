import file_manager.doman.file_manager_use_cases.FileManagerUseCases
import file_manager.doman.file_manager_use_cases.ui_out.UiOuter
import file_manager.doman.file_manager_use_cases.ui_out.UpdateOut
import io.mockk.coVerify
import io.mockk.spyk
import org.junit.jupiter.api.Test

class FileManagerUseCasesTest {

    private val uiOuter: UiOuter = spyk()
    private val useCases = FileManagerUseCases(
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