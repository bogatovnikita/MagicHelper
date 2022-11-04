package ar.cleaner.first.pf.models

import ar.cleaner.first.pf.domain.models.Junk

data class ExpandableGarbageModel(
    val type: Int, // либо PARENT либо CHILD
    val junksParent: Junks.JunkTypeFile? = null, // содержут общую информацию для инстанса с type = PARENT
    val junksChild: Junk? = null, // содержут общую информацию для инстанса с type = CHILD
    var isExpanded: Boolean = false, // требуется для показа чаелдов или их скрытия в списке
    var checkedCountElements: Int = 0,
    var checkedFilesSize: Long = 0L, // ---остальные перменные необходимы для показа полей (https://www.google.com/search?q=%D0%BA%D1%8D%D0%BF+%D0%BC%D0%B5%D0%BC&tbm=isch&ved=2ahUKEwjoidj0vaX5AhXNAhAIHTrvAKsQ2-cCegQIABAA&oq=%D0%BA%D1%8D%D0%BF+%D0%BC%D0%B5%D0%BC&gs_lcp=CgNpbWcQAzIFCAAQgAQyBggAEB4QBToECAAQQzoGCAAQHhAHOggIABCxAxCDAToLCAAQgAQQsQMQgwE6CAgAEIAEELEDOgYIABAeEAg6BAgAEBhQ3QdYlylgmitoAHAAeACAAT6IAeICkgEBOJgBAKABAaoBC2d3cy13aXotaW1nsAEAwAEB&sclient=img&ei=qLHnYuiVEc2FwPAPut6D2Ao&bih=746&biw=1536#imgrc=Pqw0KdUYGTjHpM)---
    val generalSize: Long = 0L,
    val generalCountFiles: Int = 0
) {

    companion object {
        const val PARENT = 1
        const val CHILD = 2
    }
}