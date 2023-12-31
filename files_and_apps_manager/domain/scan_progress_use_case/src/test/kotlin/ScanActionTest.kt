import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.Ads
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.scan.ScanActionImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import yin_kio.file_app_manager.updater.ContentUpdater


@OptIn(ExperimentalCoroutinesApi::class)
class ScanActionTest {

    private val uiOuter: UiOuter = spyk()
    private val delayer: Delayer = spyk()
    private val ads: Ads = spyk()
    private val contentUpdater: ContentUpdater = spyk()


    private val useCase = ScanActionImpl(
        uiOuter = uiOuter,
        delayer = delayer,
        contentUpdater = contentUpdater,
        ads = ads
    )

    @Test
    fun testScan() = runTest{




        useCase.scan()

        coVerifyOrder {
            ads.preloadAd()
            uiOuter.showProgress()
            contentUpdater.updateFilesAndApps()
            delayer.makeDelay()
            uiOuter.showInter()
        }
    }

}