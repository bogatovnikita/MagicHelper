package yin_kio.files_and_apps_manager.presentation.overview

import file_manager.domain.server.GroupName

internal data class ScreenState(
    val groupName: GroupName = GroupName.Audio
)