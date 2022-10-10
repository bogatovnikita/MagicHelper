package ar.cleaner.first.pf.utils

import android.content.res.Resources
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.widget.TextView
import androidx.navigation.NavController
import java.lang.Exception

val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics).toInt()

fun TextView.setUnderline(){
    val text = this.text
    val content = SpannableString(text)
    content.setSpan(UnderlineSpan(), 0, text.length, 0)
    this.text = content
}

fun NavController.navigateTo(id: Int, bundle: Bundle? = null){
    try{
        navigate(id, bundle)
    } catch (e: Exception){}
}