package yin_kio.file_grouper

import file_manager.domain.server.FileOrApp
import file_manager.domain.server.GroupName
import file_manager.domain.server.selectable_form.SelectableForm
import file_manager.domain.server.selectable_form.SimpleSelectableForm
import kotlin.io.path.Path
import kotlin.io.path.extension

class GrouperImpl : Grouper {

    private val fileGroups = FileGroups()

    override fun groupFilesAndApps(
        files: List<FileOrApp>,
        apps: List<FileOrApp>
    ): Map<GroupName, SelectableForm<FileOrApp>> {

        val images = mutableListOf<FileOrApp>()
        val audio = mutableListOf<FileOrApp>()
        val documents = mutableListOf<FileOrApp>()
        val video = mutableListOf<FileOrApp>()

        files.forEach {
            val extension = Path(it.id).extension

            when{
                fileGroups.isAudio(extension) -> audio.add(it)
                fileGroups.isImage(extension) -> images.add(it)
                fileGroups.isDocument(extension) -> documents.add(it)
                fileGroups.isVideo(extension) -> video.add(it)
            }
        }

        val imagesForm = SimpleSelectableForm<FileOrApp>()
        val audioForm = SimpleSelectableForm<FileOrApp>()
        val documentsForm = SimpleSelectableForm<FileOrApp>()
        val videoForm = SimpleSelectableForm<FileOrApp>()
        val appsForm = SimpleSelectableForm<FileOrApp>()

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