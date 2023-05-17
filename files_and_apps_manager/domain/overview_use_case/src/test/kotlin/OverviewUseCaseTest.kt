import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteAction
import file_manager.doman.overview.use_case.OverviewUseCase
import file_manager.doman.overview.use_case.UpdateAction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test

class OverviewUseCaseTest {

    private val uiOuter: UiOuter = spyk()
    private val outCreator: OutCreator = mockk()
    private val server: FileManagerServer = spyk()
    private val deleteAction: DeleteAction = spyk()
    private val updateAction: UpdateAction = spyk()

    private val useCases = OverviewUseCase(
        uiOuter = uiOuter,
        outCreator = outCreator,
        server = server,
        deleteAction = deleteAction,
        updateAction = updateAction
    )


    @Test
    fun testClose(){
        useCases.close()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testUpdate(){
        useCases.update()

        coVerify { updateAction.update() }
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

        useCases.switchAllSelection(GroupName.Video)

        coVerify {
            server.switchAllSelection(GroupName.Video)
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

        useCases.switchItemSelection(GroupName.Video, itemId)

        coVerify {
            server.switchItemSelection(GroupName.Video, itemId)
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
        val groupName = GroupName.Video
        useCases.delete(groupName)

        coVerify { deleteAction.deleteAndUpdate(groupName) }
    }

    @Test
    fun testCompleteDelete(){
        useCases.completeDelete()

        coVerifySequence {
            uiOuter.hideDeleteDialog()
        }
    }

}