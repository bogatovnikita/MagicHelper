import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.OutCreator
import file_manager.doman.overview.use_cases.OverviewUseCases
import file_manager.doman.overview.ui_out.*
import file_manager.doman.overview.use_cases.DeleteUseCase
import file_manager.doman.overview.use_cases.UpdateUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class OverviewUseCasesTest {

    private val uiOuter: UiOuter = spyk()
    private val outCreator: OutCreator = mockk()
    private val server: FileManagerServer = spyk()
    private val deleteUseCase: DeleteUseCase = spyk()
    private val updateUseCase: UpdateUseCase = spyk()

    private val useCases = OverviewUseCases(
        uiOuter = uiOuter,
        outCreator = outCreator,
        server = server,
        deleteUseCase = deleteUseCase,
        updateUseCase = updateUseCase
    )


    @Test
    fun testClose(){
        useCases.close()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testUpdate(){
        useCases.update()

        coVerify { updateUseCase.update() }
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

    @Test
    fun testShowDeleteDialog(){
        assertAskIfHasSelected()
        assertDoNotAskIfHasNotSelected()
    }

    private fun assertAskIfHasSelected() {
        coEvery { server.hasSelected } returns true

        useCases.showDeleteDialog()

        coVerify { uiOuter.showDeleteDialog() }
    }

    private fun assertDoNotAskIfHasNotSelected(){
        coEvery { server.hasSelected } returns false

        useCases.showDeleteDialog()

        coVerify(exactly = 1) { uiOuter.showDeleteDialog() }
    }

    @Test
    fun testHideDeleteDialog(){
        useCases.hideDeleteDialog()

        coVerify { uiOuter.hideDeleteDialog() }
    }

    @Test
    fun testDelete(){
        useCases.delete()

        coVerify { deleteUseCase.delete() }
    }

}