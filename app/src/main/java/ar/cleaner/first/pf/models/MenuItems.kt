package ar.cleaner.first.pf.models

data class MenuItems(
    val id: Int,
    val title: String,
    val icon: Int,
    var descriptionIsOptimize: String,
    var descriptionNotOptimize: String,
    var optimizeIsDone: Boolean
)