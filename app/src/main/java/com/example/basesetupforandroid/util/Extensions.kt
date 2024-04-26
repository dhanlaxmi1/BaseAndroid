package com.example.basesetupforandroid.util

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.Serializable


fun Fragment.reqAct() = requireActivity() as AppCompatActivity

/*---------------------------------------------------------------*/

/*** Intent */


/**
 * startNewActivity<HomeActivity>(
 *     "hashMapExtra" to myHashMap,
 *     "keyInt" to 42,
 *     "keyString" to "Hello, World!",
 *     "keyBoolean" to true,
 *     flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
 * */

inline fun <reified T : Any> Context.startNewActivity(
    vararg extras: Pair<String, Any?>,
    flags: Int = 0,
) {
    val intent = Intent(this, T::class.java)
    extras.forEach { (key, value) ->
        when (value) {
            is Int -> intent.putExtra(key, value)
            is Long -> intent.putExtra(key, value)
            is Float -> intent.putExtra(key, value)
            is Double -> intent.putExtra(key, value)
            is Boolean -> intent.putExtra(key, value)
            is String -> intent.putExtra(key, value)
            is Serializable -> intent.putExtra(key, value)
            is HashMap<*, *> -> intent.putExtra(key, value as Serializable)
            else -> throw IllegalArgumentException("Unsupported extra type")
        }
    }
    intent.flags = flags
    startActivity(intent)
}
/*---------------------------------------------------------------*/

/**
 * To get Color, Resource, String from xml or res folder
 * Usage :
 *
 * getStr :
 *      getStr(R.string.your_string)
 *
 * getRes :
 *      getRes(R.drawable.your_drawable)
 *
 * getClr :
 *      getClr(R.color.your_color)
 *
 * getDimen :
 *      getDimen(R.dimen.your_dimension)
 *
 * */

fun Context.getStr(@StringRes id: Int) = resources.getString(id)

fun Context.getRes(@DrawableRes id: Int) = ResourcesCompat.getDrawable(resources, id, theme)!!

fun Context.getClr(@ColorRes id: Int) = ResourcesCompat.getColor(resources, id, theme)

fun Context.getClrStateList(@ColorRes id: Int) = ColorStateList.valueOf(getClr(id))

fun Context.getDimen(@DimenRes id: Int) = resources.getDimension(id)

/**
*
*
*
* */

fun ImageView.setRes(@DrawableRes drawableRes: Int? = null, @DrawableRes backgroundRes: Int? = null) {
    drawableRes?.let {
        setImageResource(drawableRes)
    }

    backgroundRes?.let {
        setBackgroundResource(it)
    }
}

fun View.setBgRes(@DrawableRes drawableRes: Int) {
    val drawable = context.getRes(drawableRes)
    background = drawable
}

fun View.setBgClr(@ColorRes colorRes: Int) {
    val color = context.getClr(colorRes)
    setBackgroundColor(color)
}

fun View.setBgTint(@ColorInt color: Int) {
    ViewCompat.setBackgroundTintList(this, android.content.res.ColorStateList.valueOf(color))
}

fun TextView.setTextClr(colorResId: Int) {
    this.setTextColor(context.getClr(colorResId))
}

fun TextView.setLeftDrawable(drawable: Int) {
    val icon: Drawable = context.getRes(drawable)
    setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
}


/*---------------------------------------------------------------*/

/**
 * VIEW VISIBILITY
 * */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.setVisibleView(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


/*---------------------------------------------------------------*/

/**
 * Inflate view in adapter
 *
 * Usage :
 *      1.) parent.inflate(R.layout.my_layout) -> default root is false
 *      2.) parent.inflate(R.layout.my_layout, true)
 * */
fun ViewGroup.inflateWithView(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <reified T : ViewBinding> LayoutInflater.inflateWithInflater(
    @LayoutRes layoutRes: Int,
    viewGroup: ViewGroup?,
    attachToRoot: Boolean = false,
): T =
    (DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(this.context),
        layoutRes,
        viewGroup,
        attachToRoot
    ) as T)

fun Context.inflateView(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(this).inflate(layoutRes, null)

inline fun <reified T : ViewBinding> ViewGroup.inflateAdapter(@LayoutRes layoutRes: Int): T {
    return (DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(this.context),
        layoutRes,
        this,
        false
    ) as T)
}

fun Context.inflateDialogBinding(@LayoutRes layoutResId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this),
        layoutResId,
        null,
        false
    )
}

inline fun <reified T : ViewBinding> Context.inflateBindingView(@LayoutRes layoutRes: Int): T {
    return T::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, LayoutInflater.from(this)) as T
}

/*---------------------------------------------------------------*/

fun View.asString(): String {
    return when (this) {
        is EditText -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is AppCompatEditText -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is AppCompatTextView -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is AutoCompleteTextView -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is MaterialButton -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        else -> ""
    }
}


inline fun <T : Any, R> T?.ifNotNullOrElse(ifNotNullPath: (T) -> R, elsePath: () -> R) =
    let { if (it == null) elsePath() else ifNotNullPath(it) }

/*---------------------------------------------------------------*/

/**Log**/
fun String.loge(tag: String = "--->E") {
    val LOG_TRACE_REQUIRED = true
    if (LOG_TRACE_REQUIRED && isNotEmpty()) {
        val maxLogSize = 4000
        for (i in indices step maxLogSize) {
            val end = if (i + maxLogSize < length) i + maxLogSize else length
            Log.e(tag, substring(i, end))
        }
    }
}


/**Toast*/
fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}



/**
 * showDialog(
 *     title = "Title",
 *     message = "Your message here",
 *     positiveText = "OK",
 *     negativeText = "Cancel",
 *     positiveAction = {
 *         // Positive button clicked
 *         // Do something...
 *     },
 *     negativeAction = {
 *         // Negative button clicked
 *         // Do something...
 *     }
 * )*/


fun Context.showDialog(
    title: String,
    message: String,
    positiveText: String = "OK",
    negativeText: String? = null,
    positiveAction: (() -> Unit)? = null,
    negativeAction: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveText) { dialog, _ ->
            positiveAction?.invoke()
            dialog.dismiss()
        }

    if (negativeText != null) {
        builder.setNegativeButton(negativeText) { dialog, _ ->
            negativeAction?.invoke()
            dialog.dismiss()
        }
    }

    builder.create().show()
}


/**----------------------------------------------------*/
inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}

inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}

suspend fun <T> await(block: () -> Deferred<T>): T = block().await()

suspend fun <T> awaitAsync(block: () -> T): T = coroutineScope { async { block() }.await() }

fun coroutineAsync(runner: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Default).launch { runner.invoke((this)) }

fun coroutineUI(runner: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Main).launch { runner.invoke((this)) }