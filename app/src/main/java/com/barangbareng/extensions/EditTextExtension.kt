package com.barangbareng.extensions

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText


fun EditText.afterTextChanged(afterTextChanged: String.() -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(editable: Editable?) =
            afterTextChanged.invoke(editable.toString())
    })
}

private val EMOJI_FILTER = InputFilter { source, start, end, _, _, _ ->
    for (index in start until end) {
        val type: Int = Character.getType(source[index])
        if (type == Character.SURROGATE.toInt()) {
            return@InputFilter ""
        }
    }
    return@InputFilter null
}

fun EditText.removeEmoji() {
    this.filters = arrayOf(EMOJI_FILTER)
}

fun EditText.removeEmojiMaxLength(maxLength: Int) {
    this.filters = arrayOf(EMOJI_FILTER, InputFilter.LengthFilter(maxLength))
}
