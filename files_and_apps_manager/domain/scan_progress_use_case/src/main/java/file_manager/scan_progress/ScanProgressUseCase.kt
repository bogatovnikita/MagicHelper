package file_manager.scan_progress

interface ScanProgressUseCase {
    fun close()
    fun requestPermission()
    fun scan()
}