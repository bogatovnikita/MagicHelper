package file_manager.scan_progress.grouper

import file_manager.domain.server.GroupName
import file_manager.domain.server.selectable_form.SelectableForm
import file_manager.domain.server.selectable_form.SimpleSelectableForm
import kotlin.io.path.Path
import kotlin.io.path.extension

class GrouperImpl : Grouper {

    private val fileGroups = FileGroups()

    override fun groupFilesAndApps(
        files: List<String>,
        apps: List<String>
    ): Map<GroupName, SelectableForm<String>> {

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

        val imagesForm = SimpleSelectableForm<String>()
        val audioForm = SimpleSelectableForm<String>()
        val documentsForm = SimpleSelectableForm<String>()
        val videoForm = SimpleSelectableForm<String>()
        val appsForm = SimpleSelectableForm<String>()

        imagesForm.content = images
        audioForm.content = audio
        documentsForm.content = documents
        videoForm.content = video
        appsForm.content = apps

        return mapOf(
            GroupName.Images to imagesForm,
            GroupName.Video to videoForm,
            GroupName.Audio to audioForm,
            GroupName.Documents to documentsForm,
            GroupName.Apps to appsForm,
        ).filter { it.value.content.isNotEmpty() }
    }
}