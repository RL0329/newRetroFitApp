package com.example.ayabeltran.firstproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lorenzo11 on 09/02/2018.
 */

public class PwordEditText extends android.support.v7.widget.AppCompatEditText{

    public PwordEditText(Context context) {
        super(context);
        init();
    }

    public PwordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    final Drawable mbtnToggleVis = getResources().getDrawable(R.drawable.eye);

    private void init() {

        mbtnToggleVis.setBounds(0, 0, 100, 100);
        final int con = 1;
        this.setTransformationMethod(PasswordTransformationMethod.getInstance());

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                VisBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PwordEditText mPwordEditText =PwordEditText.this;
                if (con == 1) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mPwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            break;
                        case MotionEvent.ACTION_UP:
                            mPwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            break;
                    }
                }
                return false;
            }
        });
    }
    void VisBtn(){
        if (PwordEditText.this.getText().toString().isEmpty()) {
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
        } else {
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], mbtnToggleVis, this.getCompoundDrawables()[3]);
        }
    }
}