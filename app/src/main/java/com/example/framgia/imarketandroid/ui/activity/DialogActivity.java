package com.example.framgia.imarketandroid.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.DialogShareUtil;

public class DialogActivity extends AppCompatActivity {
    public static String sFirstPoint, sSecondPoint;
    private EditText mFirstText, mSecondText;
    private Button mBtnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mFirstText = (EditText) findViewById(R.id.first_point);
        mSecondText = (EditText) findViewById(R.id.second_point);
        mBtnAccept = (Button) findViewById(R.id.done);
        mBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first, second;
                first = mFirstText.getText().toString();
                second = mSecondText.getText().toString();
                if (first.length() > 0 && second.length() > 0) {
                    sFirstPoint = first;
                    sSecondPoint = second;
                    finish();
                } else {
                    DialogShareUtil.toastDialogMessage(getString(R.string.warning_text),
                        DialogActivity.this);
                }
            }
        });
    }
}