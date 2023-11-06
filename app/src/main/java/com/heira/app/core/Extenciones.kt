package com.heira.app.core

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChange(listener: (String)-> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            true
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {
            true
        }

    })
}