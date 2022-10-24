package ar.cleaner.first.pf.data.extensions

import android.content.Context
import android.text.format.Formatter
import ar.cleaner.first.pf.domain.models.JunkFile
import java.io.File

fun File.getSizeOfFile(context: Context) = Formatter.formatFileSize(
    context,
    length()
)

fun File.asJunkFile(context: Context): JunkFile = JunkFile(
    name = this.name + this.extension,
    filePath = this.absolutePath,
    sizeReadable = getSizeOfFile(context),
    size = (length()).toDouble()
)
