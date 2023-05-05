package file_manager.scan_progress.gateways

interface FilesAndApps {

    fun provideFiles() : List<String>
    fun provideApps() : List<String>

}