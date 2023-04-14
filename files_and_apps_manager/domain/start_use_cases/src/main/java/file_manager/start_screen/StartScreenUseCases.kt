package file_manager.start_screen

class StartScreenUseCases(
    private val uiOuter: UiOuter,
    private val usedMem: UsedMem,
    private val cleanChecker: CleanChecker
) {


    fun close(){
        uiOuter.close()
    }

    fun scan(){
        uiOuter.showScanProgress()
    }

    fun update(){
        val usedMemOut = usedMem.provide()
        val isCleaned = cleanChecker.isCleaned

        val updateOut = UpdateOut(
            usedMemOut = usedMemOut,
            isCleaned = isCleaned
        )

        uiOuter.out(updateOut)
    }

}