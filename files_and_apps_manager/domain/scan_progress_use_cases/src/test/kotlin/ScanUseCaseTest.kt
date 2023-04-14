import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.scan.FilesAndApps
import file_manager.scan_progress.scan.ScanUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.File


@OptIn(ExperimentalCoroutinesApi::class)
class ScanUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val delayer: Delayer = spyk()
    private val server: FileManagerServer = spyk()
    private val filesAndApps: FilesAndApps = mockk()

    private val useCase = ScanUseCaseImpl(
        uiOuter = uiOuter,
        delayer = delayer,
        filesAndApps = filesAndApps,
        fileManagerServer = server
    )

    @Test
    fun testScan() = runTest{
        val filesList: List<File> = emptyList()
        coEvery { filesAndApps.provide() } returns filesList

        useCase.scan()

        coVerifyOrder {
            uiOuter.showProgress()
            server.setFilesAndApps(filesList)
            delayer.makeDelay()
            uiOuter.showInter()
        }
    }

}