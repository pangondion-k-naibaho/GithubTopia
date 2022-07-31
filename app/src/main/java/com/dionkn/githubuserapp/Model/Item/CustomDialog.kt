package com.dionkn.githubuserapp.Model.Item

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Window
import com.dionkn.githubuserapp.R
import com.dionkn.githubuserapp.databinding.PopupOnfailureBinding

interface PopupDialogListener{
    fun onClickListener()
}

fun Activity.showPopupDialog(
    drawable: Drawable,
    textDesc: String,
    textButton: String,
    listener: PopupDialogListener?= null,
){
    val dialog = Dialog(this)
    val binding = PopupOnfailureBinding.bind(layoutInflater.inflate(R.layout.popup_onfailure, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)
    dialog.setCancelable(listener == null)
    binding.apply {
        ivPopupOnFailure.setImageDrawable(drawable)
        tvPopupOnFailure.text = textDesc
        btnPopupOnFailure.text = textButton
        btnPopupOnFailure.setOnClickListener {
            listener?.onClickListener()
            dialog.dismiss()
        }
        if(!isFinishing) dialog.show()
    }
}