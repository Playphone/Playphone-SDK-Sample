package com.playphone.sdk3sample;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.playphone.psgn.PSGN;
import com.playphone.sdk3sample.helper.ChildActivity;

public class PostAchievementActivity extends ChildActivity implements OnClickListener {
    EditText editInput;
    TextView txtResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_achievement);

        // set the breadcrumbs text
        TextView txtBreadCrumbs = (TextView) findViewById(R.id.txtBreadCrumbs);
        txtBreadCrumbs.setText("Home > Achievements");


        Button btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);

        editInput = (EditText) findViewById(R.id.editInput);
        txtResult = (TextView) findViewById(R.id.txtResult);

        editInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editInput.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

    }


    @Override
    public void onClick(View arg0) {
        if (editInput.getText().toString().length() > 0) {
            PSGN.Achievements.unlock(editInput.getText().toString());
        } else {
            Toast.makeText(PostAchievementActivity.this, "Please enter a number to test achievements.", Toast.LENGTH_SHORT).show();
        }
    }

}
