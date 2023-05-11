package file_manager.doman.overview.use_case

import file_manager.domain.server.GroupName

interface DeleteUseCase {

    fun deleteAndUpdate(groupName: GroupName)

}