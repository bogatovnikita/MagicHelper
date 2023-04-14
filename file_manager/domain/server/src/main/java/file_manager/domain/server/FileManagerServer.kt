package file_manager.domain.server

import java.io.File

interface FileManagerServer {

    fun setFiles(files: List<File>)

}