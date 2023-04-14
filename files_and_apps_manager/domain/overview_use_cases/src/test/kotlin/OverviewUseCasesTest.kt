import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.AllSelectionOutProvider
import file_manager.doman.overview.OverviewUseCases
import file_manager.doman.overview.UpdateOutProvider
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.GroupName
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class OverviewUseCasesTest {

    private val uiOuter: UiOuter = spyk()
    private val updateOutProvider: UpdateOutProvider = mockk()
    private val allSelectionOutProvider: AllSelectionOutProvider = mockk()
    private val server: FileManagerServer = spyk()

    private val useCases = OverviewUseCases(
        uiOuter = uiOuter,
        updateOutProvider = updateOutProvider,
        allSelectionOutProvider = allSelectionOutProvider,
        server = server,
    )

    @Test
    fun testClose(){
        useCases.close()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testUpdate(){
        val updateOut = UpdateOut()
        coEvery { updateOutProvider.provide() } returns updateOut

        useCases.update()

        coVerify { uiOuter.out(updateOut) }
    }

    @Test
    fun testSwitchGroup(){
        GroupName.values().forEach {
            useCases.switchGroup(it)

            coVerify { uiOuter.showGroup(it) }
        }

    }

    @Test
    fun testSwitchAllSelection(){
        val allSelectionOut = AllSelectionOut(selectedCount = 10)
        coEvery { allSelectionOutProvider.provide() } returns allSelectionOut

        useCases.switchAllSelection()

        coVerify {
            server.switchAllSelection()
            uiOuter.out(allSelectionOut)
        }
    }

}