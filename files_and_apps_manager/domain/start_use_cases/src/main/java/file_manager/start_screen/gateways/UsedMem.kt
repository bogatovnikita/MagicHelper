package file_manager.start_screen.gateways

import file_manager.start_screen.ui_out.UsedMemOut

interface UsedMem {

    fun provide() : UsedMemOut

}