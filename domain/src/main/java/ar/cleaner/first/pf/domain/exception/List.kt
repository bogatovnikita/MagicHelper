package ar.cleaner.first.pf.domain.exception

import ar.cleaner.first.pf.domain.const.Const

inline fun <T> List<T>?.checkEmptyAndSum(selector: (T) -> Double): Double {
    return this?.let {
        return@let it.sumOf(selector)
    } ?: Const.DEFAULT_DOUBLE_VALUE
}

inline fun <T> List<T>.addToCollection(collection: MutableCollection<T>) {
    forEach {
        collection.add(it)
    }
}
