import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.OutCreator
import file_manager.doman.overview.OverviewUseCases
import file_manager.doman.overview.ui_out.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class OverviewUseCasesTest {

    private val uiOuter: UiOuter = spyk()
    private val outCreator: OutCreator = mockk()
    private val server: FileManagerServer = spyk()

    private val useCases = OverviewUseCases(
        uiOuter = uiOuter,
        outCreator = outCreator,
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
        coEvery { outCreator.createUpdateOut() } returns updateOut

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
        coEvery { outCreator.createAllSelectionOut() } returns allSelectionOut

        useCases.switchAllSelection()

        coVerify {
            server.switchAllSelection()
            uiOuter.out(allSelectionOut)
        }
    }

    @Test
    fun testShowSortingSelection(){
        useCases.showSortingSelection()

        coVerify { uiOuter.showSortingSelection() }
    }

    @Test
    fun testSwitchItemSelection(){
        val itemId = "some_id"
        val itemSelectionOut = ItemSelectionOut(id = itemId)

        coEvery { outCreator.createItemSelectionOut(itemId) } returns itemSelectionOut

        useCases.switchItemSelection(itemId)

        coVerify {
            server.switchItemSelection(itemId)
            uiOuter.out(itemSelectionOut)
        }
    }

}