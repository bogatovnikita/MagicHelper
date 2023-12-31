package ar.cleaner.second.pf.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BackgroundAppsModel(
    val name: String,
    val packageName: String
): Parcelable
