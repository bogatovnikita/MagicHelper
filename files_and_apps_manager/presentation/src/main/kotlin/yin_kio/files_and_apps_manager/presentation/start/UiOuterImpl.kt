package yin_kio.files_and_apps_manager.presentation.start

import file_manager.start_screen.ui_out.UiOuter
import file_manager.start_screen.ui_out.UpdateOut

internal class UiOuterImpl(
    private val presenter: Presenter
) : UiOuter {

    var viewModel: ViewModel? = null

    override suspend fun close() {
        viewModel?.sendCommand(Command.Close)
    }

    override suspend fun showScanProgress() {
        viewModel?.sendCommand(Command.ShowScanProgress)
    }

    override fun out(updateOut: UpdateOut) {
        val occupied = updateOut.usedMemOut.occupied
        val total = updateOut.usedMemOut.total
        val progress = presenter.presentProgress(occupied, total)
        viewModel?.update { it.copy(
            percents = presenter.presentPercents(progress),
            occupiedAndTotal = presenter.presentOccupiedAndTotal(occupied, total),
            progress = progress,
            dangerColor = presenter.presentDangerColor(updateOut.isCleaned),
            dangerText = presenter.presentDangerText(updateOut.isCleaned),
            dangerBg = presenter.presentButtonBg(updateOut.isCleaned)
        ) }
    }
}