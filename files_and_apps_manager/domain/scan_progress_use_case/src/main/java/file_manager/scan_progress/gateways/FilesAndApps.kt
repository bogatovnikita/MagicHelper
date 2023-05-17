package file_manager.scan_progress.gateways

import file_manager.domain.server.FileOrApp

interface FilesAndApps {

    fun provideFiles() : List<FileOrApp>
    fun provideApps() : List<FileOrApp>

}