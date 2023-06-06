package yin_kio.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import yin_kio.toolbar.databinding.ViewTolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {


    private lateinit var  binding: ViewTolbarBinding

    init {
        inflateView()
        initializeAttributes(attrs)
    }

    private fun inflateView(){
        val inflater = LayoutInflater.from(context)
        binding = ViewTolbarBinding.inflate(inflater, this)
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.withStyledAttributes(
            attrs,
            R.styleable.ToolbarView
        ){
            val titleTextToolbar = getString(R.styleable.ToolbarView_title_text)
            binding.titleTv.text = titleTextToolbar ?: DEFAULT_TITLE

            val isArrowBack = getBoolean(R.styleable.ToolbarView_is_arrow_back, true)
            binding.arrowBackIv.isVisible = isArrowBack

        }

    }
    companion object{
        private const val DEFAULT_TITLE = "Title"
    }


}
