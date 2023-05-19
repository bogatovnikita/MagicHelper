package yin_kio.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import yin_kio.toolbar.databinding.ViewTolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {


    val binding: ViewTolbarBinding

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_tolbar, this, true)
        binding = ViewTolbarBinding.bind(view)
        initializeAttributes(attrs)

    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarView
        )
        val titleTextToolbar = typedArray.getString(R.styleable.ToolbarView_title_text)
        binding.titleTv.text = titleTextToolbar ?: ""

        val arrowBack = typedArray.getBoolean(R.styleable.ToolbarView_arrow_back, true)
        binding.arrowBackIv.visibility = if (arrowBack) VISIBLE else GONE

    }


}
