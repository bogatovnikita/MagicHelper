package yin_kio.files_and_apps_manager.presentation.overview

internal sealed interface Command{

    object Close : Command
    object ShowAskDeleteDialog : Command
    object ShowUpdateAppsProgress : Command
    object HideUpdateAppsProgress : Command
    object HideAskDeleteDialog : Command
    object ShowDeleteProgress : Command
    object ShowDeleteCompletion : Command
    object UpdateListContent : Command
    object HideDoneDialog  : Command
    data class DeleteApps(val ids: List<String>) : Command

}