import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.GroupOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.OutCreatorImpl
import file_manager.doman.overview.ui_out.UpdateOut
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OutCreatorTest {

    private val server: FileManagerServer = mockk()

    private val outCreator = OutCreatorImpl(
        server = server
    )


    @Test
    fun testCreateAllSelectionOut(){
        assertAllSelectedOut_correctCreation(2, true)
        assertAllSelectedOut_correctCreation(1, false)
    }

    private fun assertAllSelectedOut_correctCreation(selectedCount: Int, isAllSelected: Boolean) {
        coEvery { server.selectedCount } returns selectedCount
        coEvery { server.isAllSelected } returns isAllSelected

        val expected = AllSelectionOut(
            isAllSelected = isAllSelected,
            selectedCount = selectedCount
        )

        val actual = outCreator.createAllSelectionOut()

        assertEquals(expected, actual)
    }

    @Test
    fun testCreateItemSelectionOut(){
        assertItemSelectionOut_correctCreation(true, "itemId", true, 1)
        assertItemSelectionOut_correctCreation(false, "itemId2", false, 2)
    }

    private fun assertItemSelectionOut_correctCreation(
        isAllSelected: Boolean,
        itemId: String,
        isItemSelected: Boolean,
        selectedCount: Int
    ) {
        coEvery { server.isAllSelected } returns isAllSelected
        coEvery { server.isItemSelected(itemId) } returns isItemSelected
        coEvery { server.selectedCount } returns selectedCount

        val expected = ItemSelectionOut(
            id = itemId,
            isItemSelected = isItemSelected,
            isAllSelected = isAllSelected,
            selectedCount = selectedCount
        )

        val actual = outCreator.createItemSelectionOut(itemId)

        assertEquals(expected, actual)
    }

    @Test
    fun testCreateUpdateOut(){
        assertCorrectUpdateOutCreation(input = emptyMap(), expected = emptyList())
        assertCorrectUpdateOutCreation(
            input = mapOf(GroupName.Images to listOf("some_id")),
            expected = listOf(GroupOut(name = GroupName.Images, ids = listOf("some_id")))
        )

    }

    private fun assertCorrectUpdateOutCreation(
        input: Map<GroupName, List<String>>,
        expected: List<GroupOut>
    ) {
        coEvery { server.groups } returns input
        coEvery { server.isAllSelected } returns false
        coEvery { server.selectedCount } returns 0

        val copiedExpected = UpdateOut(
            groups = expected.map { it.copy() }
        )

        val actual = outCreator.createUpdateOut()

        assertEquals(copiedExpected, actual)
    }


}