package yin_kio.file_app_manager.updater

import file_manager.domain.server.FileManagerServer
import yin_kio.file_grouper.Grouper

class UpdaterImpl(
    private val filesAndApps: FilesAndApps,
    private val server: FileManagerServer,
    private val grouper: Grouper
) : Updater {

    override suspend fun update(){

        val files = filesAndApps.provideFiles()
        val apps = filesAndApps.provideApps()


        server.groups = grouper.groupFilesAndApps(
            files = files,
            apps = apps
        )

    }

}