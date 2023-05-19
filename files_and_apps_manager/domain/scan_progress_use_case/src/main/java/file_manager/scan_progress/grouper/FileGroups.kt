package file_manager.scan_progress.grouper

class FileGroups {
    private val video = mapOf(
        "WEBM" to true,
        "MKV" to true,
        "FLV" to true,
        "VOB" to true,
        "OGV" to true,
        "OGG" to true,
        "DRC" to true,
        "GIF" to true,
        "GIFV" to true,
        "MNG" to true,
        "AVI" to true,
        "MTS" to true,
        "M2TS" to true,
        "TS" to true,
        "MOV" to true,
        "QT" to true,
        "WMV" to true,
        "YUV" to true,
        "RM" to true,
        "RMVB" to true,
        "VIV" to true,
        "ASF" to true,
        "AMV" to true,
        "MP4" to true,
        "M4P" to true,
        "M4V" to true,
        "MPG" to true,
        "MP2" to true,
        "MPEG" to true,
        "MPE" to true,
        "MPV" to true,
        "MPG" to true,
        "MPEG" to true,
        "M2V" to true,
        "SVI" to true,
        "3GP" to true,
        "3G2" to true,
        "MXF" to true,
        "ROQ" to true,
        "NSV" to true,
        "FLV" to true,
        "F4V" to true,
        "F4P" to true,
        "F4A" to true,
        "F4B" to true
    )

    private val images = mapOf(
        "JPEG" to true,
        "JFIF" to true,
        "JPG" to true,
        "EXIF" to true,
        "TIFF" to true,
        "GIF" to true,
        "BMP" to true,
        "PNG" to true,
        "PPM" to true,
        "PGM" to true,
        "PBM" to true,
        "PNM" to true,
        "WEBP" to true,
        "HEIF" to true,
        "AVIF" to true,
        "BAT" to true,
        "BPG" to true,
        "DEEP" to true,
        "ECW" to true,
        "FITS" to true,
        "FLIF" to true,
        "ICO" to true,
        "ILBM" to true,
        "IFF" to true,
        "IMG" to true,
        "GEM" to true,
        "NRRD" to true,
        "PAM" to true,
        "PCX" to true,
        "PGF" to true,
        "PLBM" to true,
        "SGI" to true,
        "SID" to true,
        "TGA" to true,
        "TARGA" to true,
        "VICAR" to true,
        "XISF" to true,
    )

    private val documents = mapOf(
        "DOC" to true,
        "DOCX" to true,
        "HTML" to true,
        "HTM" to true,
        "ODT" to true,
        "PDF" to true,
        "XLS" to true,
        "XLSX" to true,
        "ODS" to true,
        "PPT" to true,
        "PPTX" to true,
        "TXT" to true,
    )

    private val audio = mapOf(
        "3GP" to true,
        "AA" to true,
        "AAC" to true,
        "AAX" to true,
        "ACT" to true,
        "AIFF" to true,
        "ALAC" to true,
        "AMR" to true,
        "AU" to true,
        "AWB" to true,
        "DSS" to true,
        "DVF" to true,
        "FLAC" to true,
        "GSM" to true,
        "IKLAX" to true,
        "IVS" to true,
        "M4A" to true,
        "M4B" to true,
        "M4P" to true,
        "MMF" to true,
        "MP3" to true,
        "MPC" to true,
        "MSV" to true,
        "NMF" to true,
        "OGG" to true,
        "OGA" to true,
        "MOGG" to true,
        "OPUS" to true,
        "RA" to true,
        "RM" to true,
        "RAW" to true,
        "RF64" to true,
        "SLN" to true,
        "TTA" to true,
        "VOC" to true,
        "VOX" to true,
        "WAV" to true,
        "WMA" to true,
        "WV" to true,
        "WEBM" to true,
        "8SVX" to true,
        "CDA" to true,
    )

    fun isImage(ext: String) : Boolean {
        return images[ext.uppercase()] != null
    }
    fun isVideo(ext: String) : Boolean {
        return video[ext.uppercase()] != null
    }
    fun isAudio(ext: String) : Boolean {
        return audio[ext.uppercase()] != null
    }
    fun isDocument(ext: String) : Boolean {
        return documents[ext.uppercase()] != null
    }
}