package file_manager.scan_progress.grouper

import file_manager.domain.server.GroupName
import kotlin.io.path.Path
import kotlin.io.path.extension

class GrouperImpl : Grouper {

    private val fileGroups = FileGroups()

    override fun groupFilesAndApps(
        files: List<String>,
        apps: List<String>
    ): Map<GroupName, List<String>> {

        val images = mutableListOf<String>()
        val audio = mutableListOf<String>()
        val documents = mutableListOf<String>()
        val video = mutableListOf<String>()

        files.forEach {
            val extension = Path(it).extension

            when{
                fileGroups.isAudio(extension) -> audio.add(it)
                fileGroups.isImage(extension) -> images.add(it)
                fileGroups.isDocument(extension) -> documents.add(it)
                fileGroups.isVideo(extension) -> video.add(it)
            }
        }

        return mapOf(
            GroupName.Images to images,
            GroupName.Video to video,
            GroupName.Audio to audio,
            GroupName.Documents to documents,
            GroupName.Apps to apps,
        ).filter { it.value.isNotEmpty() }
    }
}