package file_manager.doman.overview.gateways

interface AppsDeleter {

    suspend fun deleteApps(ids: List<String>)

}