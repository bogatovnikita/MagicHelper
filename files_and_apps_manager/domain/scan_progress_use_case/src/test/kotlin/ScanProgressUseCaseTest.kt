import file_manager.scan_progress.gateways.Permissions
import file_manager.scan_progress.ScanProgressUseCase
import file_manager.scan_progress.scan.ScanUseCase
import file_manager.scan_progress.UiOuter
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
class ScanProgressUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val permissions: Permissions = mockk()
    private val scanUseCase: ScanUseCase = spyk()

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    private val useCases = ScanProgressUseCase(
        uiOuter = uiOuter,
        scanUseCase = scanUseCase,
        permissions = permissions,
        coroutineScope = testScope,
        dispatcher = dispatcher
    )

    @Test
    fun testClose(){
        useCases.close()

        coVerify { uiOuter.close() }
    }

    @Test
    fun requestPermission(){
        useCases.requestPermission()

        coVerify { uiOuter.requestPermission() }
    }

    @Test
    fun testScan() = testScope.runTest{
        assertScanWithoutPermission()
        assertScanWithPermission()
    }

    private fun TestScope.assertScanWithoutPermission() {
        coEvery { permissions.hasPermissions } returns false

        useCases.scan()
        advanceUntilIdle()

        coVerify { uiOuter.showPermissionDialog() }
    }

    private fun TestScope.assertScanWithPermission(){
        coEvery { permissions.hasPermissions } returns true

        useCases.scan()
        advanceUntilIdle()

        coVerify { scanUseCase.scan() }
    }

}