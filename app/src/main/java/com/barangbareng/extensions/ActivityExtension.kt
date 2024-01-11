package com.barangbareng.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.barangbareng.R
import com.barangbareng.utils.Constants

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.sendWhatsapp(
    number: String ="6285648444086",
    message: String = "Halo min, saya mau sewa barang Camera X3 nih lewat barang bareng"
) {
    try {
        val url = Constants.WHATS_APP_LINK_NUMBER + number + Constants.WHATS_APP_LINK_TEXT + message
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    } catch (activityNotFound: ActivityNotFoundException) {
        showBlackToast(getString(R.string.alat_event))
    }
}

fun Activity.sendEmail(
    email: String,
    title: String,
    body: String
) {
    val selectorIntent = Intent(Intent.ACTION_SENDTO)
    selectorIntent.data = Uri.parse("mailto:")

    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    intent.putExtra(Intent.EXTRA_SUBJECT, title)
    intent.putExtra(Intent.EXTRA_TEXT, body)
    intent.selector = selectorIntent
    startActivity(Intent.createChooser(intent, "Send email..."))
}

fun Activity.isKeyboardOpened(): Boolean {
    val rect = Rect()
    val activityRoot = getActivityRoot()
    val visibleThreshold = dip(100)
    activityRoot.getWindowVisibleDisplayFrame(rect)
    val heightDiff = activityRoot.rootView.height - rect.height()
    return heightDiff > visibleThreshold
}


fun Activity.getActivityRoot(): View = (findViewById<ViewGroup>(android.R.id.content)).rootView

fun dip(value: Int): Int = (value * Resources.getSystem().displayMetrics.density).toInt()