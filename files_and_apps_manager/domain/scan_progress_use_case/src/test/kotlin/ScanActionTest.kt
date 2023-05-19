import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode
import file_manager.domain.server.selectable_form.SelectableForm
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.Ads
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.gateways.FilesAndApps
import file_manager.scan_progress.grouper.Grouper
import file_manager.scan_progress.scan.ScanActionImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ScanActionTest {

    private val uiOuter: UiOuter = spyk()
    private val delayer: Delayer = spyk()
    private val server: FileManagerServer = spyk()
    private val filesAndApps: FilesAndApps = mockk()
    private val grouper: Grouper = mockk()
    private val ads: Ads = spyk()


    private val useCase = ScanActionImpl(
        uiOuter = uiOuter,
        delayer = delayer,
        filesAndApps = filesAndApps,
        fileManagerServer = server,
        grouper = grouper,
        ads = ads
    )

    @Test
    fun testScan() = runTest{
        val files: List<FileOrApp> = emptyList()
        val apps: List<FileOrApp> = emptyList()
        val groups = mapOf<GroupName, SelectableForm<FileOrApp>>()

        coEvery { filesAndApps.provideFiles() } returns files
        coEvery { filesAndApps.provideApps() } returns apps
        coEvery { grouper.groupFilesAndApps(files, apps) } returns groups



        useCase.scan()

        coVerifyOrder {
            ads.preloadAd()
            uiOuter.showProgress()
            server.groups = groups
            server.setSortingMode(SortingMode.BigFirst)
            delayer.makeDelay()
            uiOuter.showInter()
        }
    }

}