package yin_kio.files_and_apps_manager.data

import file_manager.doman.overview.gateways.Deleter
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists

class DeleterImpl : Deleter {

    override fun delete(ids: List<String>) {
        ids.forEach {
            kotlin.runCatching {
                Path(it).deleteIfExists()
            }
        }
    }
}