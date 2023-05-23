package file_manager.doman.overview.gateways

interface FilesDeleter {

    suspend fun delete(ids: List<String>)

}