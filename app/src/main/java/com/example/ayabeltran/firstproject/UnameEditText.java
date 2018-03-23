package com.example.ayabeltran.firstproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Lorenzo11 on 09/02/2018.
 */

public class UnameEditText extends android.support.v7.widget.AppCompatEditText {
    public UnameEditText(Context context) {
        super(context);
        init();
    }
    public UnameEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public UnameEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    final Drawable mbtnClearText = getResources().getDrawable(R.drawable.xbtn);
    private void init(){

        mbtnClearText.setBounds(0,0,100,100);
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearText();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UnameEditText mUnameEditText = UnameEditText.this;
                mUnameEditText.setText("");
                return false;
            }
        });
    }

    void clearText(){
        if(this.getText().toString().isEmpty()){
            this.setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        }
        else{
            this.setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1], mbtnClearText, getCompoundDrawables()[3]);
        }
    }
}