package ar.cleaner.second.pf.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableJunk(
    val pathForDelete: String,
    val typeJunk: String,
): Parcelable
