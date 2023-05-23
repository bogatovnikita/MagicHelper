package yin_kio.file_app_manager.updater

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import yin_kio.file_grouper.Grouper

class ContentUpdaterImpl(
    private val filesAndApps: FilesAndApps,
    private val server: FileManagerServer,
    private val grouper: Grouper
) : ContentUpdater {



    override suspend fun updateFilesAndApps(){

        val files = filesAndApps.provideFiles()
        val apps = filesAndApps.provideApps()

        server.groups = grouper.groupFilesAndApps(
            files = files,
            apps = apps
        )

    }

    override suspend fun updateApps() {
        val apps = filesAndApps.provideApps()
        server.setGroupContent(GroupName.Apps, apps)
    }

}