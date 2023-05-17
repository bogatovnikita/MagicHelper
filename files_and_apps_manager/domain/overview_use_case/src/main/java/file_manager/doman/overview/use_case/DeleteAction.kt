package file_manager.doman.overview.use_case

import file_manager.domain.server.GroupName

interface DeleteAction {

    fun deleteAndUpdate(groupName: GroupName)

}