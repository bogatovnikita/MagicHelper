package com.yin_kio.ads

import android.content.Context
import android.util.AttributeSet
import com.ads.library.AdsManager
import com.google.android.gms.AdView

class Banner : AdView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        AdsManager.initBanner(this)
    }
}