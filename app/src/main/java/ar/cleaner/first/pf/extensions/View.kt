package ar.cleaner.first.pf.extensions

import android.widget.TextView
import com.facebook.shimmer.ShimmerFrameLayout

inline fun ShimmerFrameLayout?.disableShimmer() {
    this ?: return
    stopShimmer()
    hideShimmer()
}

inline fun TextView?.disableShimmerPlaceholder() {
    this ?: return
    minHeight = 0
    minWidth = 0
    background = null
}