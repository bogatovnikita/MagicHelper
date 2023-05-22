package yin_kio.file_app_manager.updater

import file_manager.domain.server.FileOrApp

interface FilesAndApps {

    suspend fun provideFiles() : List<FileOrApp>
    suspend fun provideApps() : List<FileOrApp>

}