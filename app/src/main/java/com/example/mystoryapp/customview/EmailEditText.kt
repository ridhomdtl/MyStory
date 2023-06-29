package com.example.mystoryapp.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mystoryapp.R

class EmailEditText : AppCompatEditText {

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
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val email = p0.toString().trim()
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    showErrorMessage()
                }else{
                    hideErrorMessage()
                }
            }
        })
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_email) as Drawable,
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
        errorMessage  = "Email address is invalid"
        super.setError(errorMessage,null)
    }

    private fun hideErrorMessage(){
        setButtonDrawables()
        errorMessage = ""
    }
}