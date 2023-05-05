import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.gateways.FilesAndApps
import file_manager.scan_progress.grouper.Grouper
import file_manager.scan_progress.scan.ScanUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ScanUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val delayer: Delayer = spyk()
    private val server: FileManagerServer = spyk()
    private val filesAndApps: FilesAndApps = mockk()
    private val grouper: Grouper = mockk()

    private val useCase = ScanUseCaseImpl(
        uiOuter = uiOuter,
        delayer = delayer,
        filesAndApps = filesAndApps,
        fileManagerServer = server,
        grouper = grouper
    )

    @Test
    fun testScan() = runTest{
        val files: List<String> = emptyList()
        val apps: List<String> = emptyList()
        val groups = mapOf<GroupName, List<String>>()

        coEvery { filesAndApps.provideFiles() } returns files
        coEvery { filesAndApps.provideApps() } returns apps
        coEvery { grouper.groupFilesAndApps(files, apps) } returns groups



        useCase.scan()

        coVerifyOrder {
            uiOuter.showProgress()
            server.groups = groups
            delayer.makeDelay()
            uiOuter.showInter()
        }
    }

}