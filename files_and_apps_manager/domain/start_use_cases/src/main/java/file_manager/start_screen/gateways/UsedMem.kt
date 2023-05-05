package file_manager.start_screen.gateways

import file_manager.start_screen.UsedMemOut

interface UsedMem {

    fun provide() : UsedMemOut

}