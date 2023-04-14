import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.scan.Files
import file_manager.scan_progress.scan.ScanUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.File


@OptIn(ExperimentalCoroutinesApi::class)
class ScanUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val delayer: Delayer = spyk()
    private val server: FileManagerServer = spyk()
    private val files: Files = mockk()

    private val useCase = ScanUseCaseImpl(
        uiOuter = uiOuter,
        delayer = delayer,
        files = files,
        fileManagerServer = server
    )

    @Test
    fun testScan() = runTest{
        val filesList: List<File> = emptyList()
        coEvery { files.provide() } returns filesList

        useCase.scan()

        coVerifyOrder {
            uiOuter.showProgress()
            server.setFiles(filesList)
            delayer.makeDelay()
            uiOuter.showInter()
        }
    }

}