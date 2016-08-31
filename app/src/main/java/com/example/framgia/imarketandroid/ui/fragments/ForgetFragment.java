package com.example.framgia.imarketandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.framgia.imarketandroid.R;

/**
 * Created by toannguyen201194 on 22/07/2016.
 */
public class ForgetFragment extends DialogFragment implements View.OnClickListener {
    private EditText mEditForgetEmail;
    private Button mButtonSend, mButtonCancel;
    private View mView;

    public static ForgetFragment newInstace() {
        return new ForgetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_forget_password, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
    }

    public void findView() {
        mEditForgetEmail = (EditText) mView.findViewById(R.id.edit_forget_password);
        mButtonSend = (Button) mView.findViewById(R.id.button_sendemail);
        mButtonSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sendemail:
                // TODO: 31/08/2016  send email to server
                dismiss();
                break;
        }
    }
}
