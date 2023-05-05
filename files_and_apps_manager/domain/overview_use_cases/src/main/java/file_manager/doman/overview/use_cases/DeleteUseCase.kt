package file_manager.doman.overview.use_cases

import file_manager.domain.server.GroupName

interface DeleteUseCase {

    fun deleteAndUpdate(groupName: GroupName)

}