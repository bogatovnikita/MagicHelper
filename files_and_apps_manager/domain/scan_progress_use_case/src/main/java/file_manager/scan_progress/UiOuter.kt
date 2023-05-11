package file_manager.scan_progress

interface UiOuter {

    suspend fun close()
    suspend fun requestPermission()
    suspend fun showPermissionDialog()
    suspend fun showInter()
    suspend fun showProgress()


}