package file_manager.scan_progress.scan

import java.io.File

interface Files {

    fun provide() : List<File>

}