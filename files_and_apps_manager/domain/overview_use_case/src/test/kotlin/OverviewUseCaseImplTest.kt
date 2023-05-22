import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.GroupSwitchingOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.Selectable
import file_manager.doman.overview.ui_out.SortingModeOut
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteAction
import file_manager.doman.overview.use_case.OverviewUseCaseImpl
import file_manager.doman.overview.use_case.UpdateUIAction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class OverviewUseCaseImplTest {

    private val uiOuter: UiOuter = spyk()
    private val outCreator: OutCreator = mockk()
    private val server: FileManagerServer = spyk()
    private val deleteAction: DeleteAction = spyk()
    private val updateUIAction: UpdateUIAction = spyk()

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    private val useCase = OverviewUseCaseImpl(
        uiOuter = uiOuter,
        outCreator = outCreator,
        server = server,
        deleteAction = deleteAction,
        updateUIAction = updateUIAction,
        coroutineScope = testScope,
        dispatcher = dispatcher
    )


    @Test
    fun testClose() = runTest{
        useCase.close()
        advanceUntilIdle()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testUpdate() = runTest{
        useCase.update()
        advanceUntilIdle()

        coVerify { updateUIAction.update() }
    }

    @Test
    fun testSwitchGroup() = runTest{
        GroupName.values().forEach {
            val out = GroupSwitchingOut(groupName = it)
            coEvery { outCreator.createGroupSwitchingOut() } returns out

            useCase.switchGroup(it)
            advanceUntilIdle()

            coVerify {
                server.selectedGroup = it
                uiOuter.out(out)
            }
        }

    }

    @Test
    fun testSwitchAllSelection() = runTest{
        val allSelectionOut = AllSelectionOut(selectedCount = 10)
        coEvery { outCreator.createAllSelectionOut() } returns allSelectionOut

        useCase.switchAllSelection(GroupName.Video)
        advanceUntilIdle()

        coVerify {
            server.switchAllSelection(GroupName.Video)
            uiOuter.out(allSelectionOut)
        }
    }

    @Test
    fun testShowSortingSelection() = runTest{
        useCase.showSortingSelection()
        advanceUntilIdle()

        coVerify { uiOuter.showSortingSelection() }
    }

    @Test
    fun testSwitchItemSelection() = runTest{
        val itemId = "some_id"
        val itemSelectionOut = ItemSelectionOut(id = itemId)
        val selectable: Selectable = spyk()

        coEvery { outCreator.createItemSelectionOut(itemId) } returns itemSelectionOut
        coEvery { server.isItemSelected(GroupName.Video, itemId) } returns true

        useCase.switchItemSelection(GroupName.Video, itemId, selectable)
        advanceUntilIdle()

        coVerify {
            server.switchItemSelection(GroupName.Video, itemId)
            uiOuter.out(itemSelectionOut)
            selectable.setSelected(true)
        }
    }

    @Test
    fun testShowDeleteDialog() = runTest{
        assertAskIfHasSelected()
        assertDoNotAskIfHasNotSelected()
    }

    private fun TestScope.assertAskIfHasSelected() {
        coEvery { server.hasSelected } returns true

        useCase.showAskDeleteDialog()
        advanceUntilIdle()

        coVerify { uiOuter.showDeleteDialog() }
    }

    private fun TestScope.assertDoNotAskIfHasNotSelected() {
        coEvery { server.hasSelected } returns false

        useCase.showAskDeleteDialog()
        advanceUntilIdle()

        coVerify(exactly = 1) { uiOuter.showDeleteDialog() }
    }

    @Test
    fun testHideDeleteDialog() = runTest{
        useCase.hideAskDeleteDialog()
        advanceUntilIdle()

        coVerify { uiOuter.hideAskDeleteDialog() }
    }

    @Test
    fun testDelete() = runTest{
        val groupName = GroupName.Video
        useCase.delete()
        coEvery { server.selectedGroup } returns groupName

        advanceUntilIdle()

        coVerify { deleteAction.deleteAndUpdate(groupName) }
    }

    @Test
    fun testCompleteDelete() = runTest{
        useCase.completeDelete()
        advanceUntilIdle()

        coVerifySequence {
            uiOuter.hideDoneDialog()
        }
    }

    @Test
    fun testSetSortingMode() = runTest{
        SortingMode.values().forEach {
            assertCorrectSortingModeOut(it)
        }

    }

    private fun TestScope.assertCorrectSortingModeOut(sortingMode: SortingMode) {
        val sortingModeOut = SortingModeOut(sortingMode = sortingMode)

        coEvery { outCreator.createSortingModeOut() } returns sortingModeOut

        useCase.setSortingMode(sortingMode)
        advanceUntilIdle()

        coVerifyOrder {
            server.setSortingMode(sortingMode)
            uiOuter.out(sortingModeOut)
        }
    }


    @Test
    fun testHideSortingSelection() = runTest{
        useCase.hideSortingSelection()
        advanceUntilIdle()

        coVerify { uiOuter.hideSortingSelection() }
    }

    @Test
    fun testUpdateSelectable() = runTest {
        val isSelected = true
        val groupName = GroupName.Images
        val itemId = ""
        val selectable: Selectable = spyk()

        coEvery { server.isItemSelected(groupName, itemId) } returns isSelected

        useCase.updateSelectable(groupName, itemId, selectable)

        coVerify { selectable.setSelected(isSelected) }
    }

    private fun runTest(
        block: suspend TestScope.() -> Unit
    ){
        testScope.runTest(testBody = block)
    }
}