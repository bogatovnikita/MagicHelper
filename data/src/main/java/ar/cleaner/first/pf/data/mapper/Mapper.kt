package ar.cleaner.first.pf.data.mapper

import android.app.usage.UsageStats
import ar.cleaner.first.pf.domain.models.BackgroundApp

fun UsageStats.asBackgroundApp(name: String) = BackgroundApp(
    packageName = packageName,
    name = name
)

