package file_manager.doman.overview.gateways

interface Deleter {

    suspend fun delete(ids: List<String>)

}