package yin_kio.file_app_manager.updater

interface ContentUpdater{
    suspend fun updateFilesAndApps()
    suspend fun updateApps()
}