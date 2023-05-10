import file_manager.start_screen.clean_checker.CleanChecker
import file_manager.start_screen.gateways.UsedMem
import file_manager.start_screen.ui_out.UiOuter
import file_manager.start_screen.ui_out.UpdateOut
import file_manager.start_screen.ui_out.UsedMemOut
import file_manager.start_screen.use_case.StartScreenUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class StartScreenUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val usedMem: UsedMem = mockk()
    private val cleanChecker: CleanChecker = mockk()

    private val useCases = StartScreenUseCaseImpl(
        uiOuter = uiOuter,
        usedMem = usedMem,
        cleanChecker = cleanChecker
    )

    @Test
    fun testClose(){
        useCases.close()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testScan(){
        useCases.scan()

        coVerify { uiOuter.showScanProgress() }
    }

    @Test
    fun testUpdate(){
        assertUpdate(true)
        assertUpdate(false)
    }

    private fun assertUpdate(isCleaned: Boolean) {
        val usedMemOut = UsedMemOut()
        val updateOut = UpdateOut(
            usedMemOut = usedMemOut,
            isCleaned = isCleaned
        )

        coEvery { usedMem.provide() } returns usedMemOut
        coEvery { cleanChecker.isCleaned } returns isCleaned



        useCases.update()

        coVerify {
            uiOuter.out(updateOut)
        }
    }

}