package com.example.mystoryapp.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mystoryapp.R

class PasswordEditText : AppCompatEditText {

    private lateinit var errorMessage: String
    private lateinit var errorImage: Drawable

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init()
    }
    private fun init(){

        errorImage = ContextCompat.getDrawable(context, R.drawable.ic_error_red) as Drawable
        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length < 8){
                    showErrorMessage()
                }else{
                    hideErrorMessage()
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_password) as Drawable,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun showErrorMessage(){
        setButtonDrawables(endOfTheText = errorImage)
        errorMessage  = "Password should be at least 8 characters"
        super.setError(errorMessage,null)
    }

    private fun hideErrorMessage(){
        setButtonDrawables()
        errorMessage = ""
    }
}