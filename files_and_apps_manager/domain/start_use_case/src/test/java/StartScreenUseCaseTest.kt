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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class StartScreenUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val usedMem: UsedMem = mockk()
    private val cleanChecker: CleanChecker = mockk()


    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    private val useCases = StartScreenUseCaseImpl(
        uiOuter = uiOuter,
        usedMem = usedMem,
        cleanChecker = cleanChecker,
        coroutineScope = testScope,
        dispatcher = dispatcher
    )

    @Test
    fun testClose() = testScope.runTest{
        useCases.close()
        advanceUntilIdle()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testScan() = testScope.runTest{
        useCases.scan()
        advanceUntilIdle()

        coVerify { uiOuter.showScanProgress() }
    }

    @Test
    fun testUpdate() = testScope.runTest{
        assertUpdateOutCorrectPassing(true)
        assertUpdateOutCorrectPassing(false)
    }

    private fun TestScope.assertUpdateOutCorrectPassing(isCleaned: Boolean) {
        val usedMemOut = UsedMemOut()
        val updateOut = UpdateOut(
            usedMemOut = usedMemOut,
            isCleaned = isCleaned
        )

        coEvery { usedMem.provide() } returns usedMemOut
        coEvery { cleanChecker.isCleaned } returns isCleaned



        useCases.update()
        advanceUntilIdle()

        coVerify {
            uiOuter.out(updateOut)
        }
    }

}