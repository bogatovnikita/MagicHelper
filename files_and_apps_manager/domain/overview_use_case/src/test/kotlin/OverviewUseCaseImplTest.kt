import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteAction
import file_manager.doman.overview.use_case.OverviewUseCaseImpl
import file_manager.doman.overview.use_case.UpdateAction
import io.mockk.coEvery
import io.mockk.coVerify
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
    private val updateAction: UpdateAction = spyk()

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    private val useCases = OverviewUseCaseImpl(
        uiOuter = uiOuter,
        outCreator = outCreator,
        server = server,
        deleteAction = deleteAction,
        updateAction = updateAction,
        coroutineScope = testScope,
        dispatcher = dispatcher
    )


    @Test
    fun testClose() = runTest{
        useCases.close()
        advanceUntilIdle()

        coVerify { uiOuter.close() }
    }

    @Test
    fun testUpdate() = runTest{
        useCases.update()
        advanceUntilIdle()

        coVerify { updateAction.update() }
    }

    @Test
    fun testSwitchGroup() = runTest{
        GroupName.values().forEach {
            useCases.switchGroup(it)
            advanceUntilIdle()

            coVerify { uiOuter.showGroup(it) }
        }

    }

    @Test
    fun testSwitchAllSelection() = runTest{
        val allSelectionOut = AllSelectionOut(selectedCount = 10)
        coEvery { outCreator.createAllSelectionOut() } returns allSelectionOut

        useCases.switchAllSelection(GroupName.Video)
        advanceUntilIdle()

        coVerify {
            server.switchAllSelection(GroupName.Video)
            uiOuter.out(allSelectionOut)
        }
    }

    @Test
    fun testShowSortingSelection() = runTest{
        useCases.showSortingSelection()
        advanceUntilIdle()

        coVerify { uiOuter.showSortingSelection() }
    }

    @Test
    fun testSwitchItemSelection() = runTest{
        val itemId = "some_id"
        val itemSelectionOut = ItemSelectionOut(id = itemId)

        coEvery { outCreator.createItemSelectionOut(itemId) } returns itemSelectionOut

        useCases.switchItemSelection(GroupName.Video, itemId)
        advanceUntilIdle()

        coVerify {
            server.switchItemSelection(GroupName.Video, itemId)
            uiOuter.out(itemSelectionOut)
        }
    }

    @Test
    fun testShowDeleteDialog() = runTest{
        assertAskIfHasSelected()
        assertDoNotAskIfHasNotSelected()
    }

    private fun TestScope.assertAskIfHasSelected() {
        coEvery { server.hasSelected } returns true

        useCases.showAskDeleteDialog()
        advanceUntilIdle()

        coVerify { uiOuter.showDeleteDialog() }
    }

    private fun TestScope.assertDoNotAskIfHasNotSelected() {
        coEvery { server.hasSelected } returns false

        useCases.showAskDeleteDialog()
        advanceUntilIdle()

        coVerify(exactly = 1) { uiOuter.showDeleteDialog() }
    }

    @Test
    fun testHideDeleteDialog() = runTest{
        useCases.hideAskDeleteDialog()
        advanceUntilIdle()

        coVerify { uiOuter.hideDeleteDialog() }
    }

    @Test
    fun testDelete() = runTest{
        val groupName = GroupName.Video
        useCases.delete(groupName)
        advanceUntilIdle()

        coVerify { deleteAction.deleteAndUpdate(groupName) }
    }

    @Test
    fun testCompleteDelete() = runTest{
        useCases.completeDelete()
        advanceUntilIdle()

        coVerifySequence {
            uiOuter.hideDeleteDialog()
        }
    }


    private fun runTest(
        block: suspend TestScope.() -> Unit
    ){
        testScope.runTest(testBody = block)
    }
}