package com.example.framgia.imarketandroid.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.activity.ChooseMarketActivity;
import com.example.framgia.imarketandroid.util.HttpRequest;

/**
 * Created by toannguyen201194 on 22/07/2016.
 */
public class SignUpFragment extends android.support.v4.app.Fragment {
    private View mView;
    private TextInputLayout mInputFullName, mInputPassword, mInputConfirmPass, mInputmail;
    private EditText mEditFullName, mEditMail, mEditPassword, mEditPasswordConfirm;
    private Button mButtonRegister;
    private ProgressDialog mProgressDialog;

    public SignUpFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_signup, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
    }

    private void findView() {
        mInputFullName = (TextInputLayout) mView.findViewById(R.id.input_text_fullname);
        mInputPassword = (TextInputLayout) mView.findViewById(R.id.input_text_password);
        mInputConfirmPass = (TextInputLayout) mView.findViewById(R.id.input_text_confirmpassword);
        mInputmail = (TextInputLayout) mView.findViewById(R.id.input_text_email);
        mEditFullName = (EditText) mView.findViewById(R.id.edit_text_fullname);
        mEditMail = (EditText) mView.findViewById(R.id.edit_text_email);
        mEditPassword = (EditText) mView.findViewById(R.id.edit_text_password);
        mEditPasswordConfirm = (EditText) mView.findViewById(R.id.edit_text_confirmpassword);
        mButtonRegister = (Button) mView.findViewById(R.id.button_register);
        mEditFullName.addTextChangedListener(new MyTextWatcher(mEditFullName));
        mEditMail.addTextChangedListener(new MyTextWatcher(mEditMail));
        mEditPasswordConfirm.addTextChangedListener(new MyTextWatcher(mEditPasswordConfirm));
        mEditPassword.addTextChangedListener(new MyTextWatcher(mEditPassword));
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!validate(mEditFullName, mInputFullName, R.string.err_msg_name)) {
            return;
        }
        if (!validate(mEditMail, mInputmail, R.string.err_msg_email)) {
            return;
        }
        if (!validate(mEditPassword, mInputPassword, R.string.err_msg_password)) {
            return;
        }
        if (!checkPasswordConfirm()) {
            return;
        } else {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.progressdialog));
            mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
            Session user = new Session(mEditFullName.getText().toString(), mEditMail.getText()
                .toString(), mEditPassword.getText().toString(),
                mEditPasswordConfirm.getText().toString());
            final UserModel signupModel = new UserModel(user);
            HttpRequest.getInstance().register(signupModel);
            HttpRequest.getInstance().setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
                @Override
                public void onLoadDataSuccess(Object object) {
                    UserModel signupModel1 = (UserModel) object;
                    mProgressDialog.dismiss();
                    if (signupModel1.getErrors().getEmail().get(0).isEmpty()) {
                        Intent intent = new Intent(getActivity(), ChooseMarketActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), signupModel1.getErrors().getEmail().get(0),
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onLoadDataFailure(String message) {
                }
            });
        }
    }

    private boolean validate(EditText editText, TextInputLayout inputLayout, int error) {
        if (editText.getText().toString().trim().isEmpty()) {
            inputLayout.setError(getString(error));
            requestFocus(editText);
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean checkPasswordConfirm() {
        String password = mEditPassword.getText().toString().trim();
        if (!mEditPasswordConfirm.getText().toString().trim().equals(password)) {
            mInputConfirmPass.setError(getString(R.string.err_msg_password_confirm));
            requestFocus(mEditPasswordConfirm);
            return false;
        } else if (mEditPasswordConfirm.getText().toString().trim().isEmpty()) {
            mInputConfirmPass.setError(getString(R.string.err_msg_password_confirm));
            return false;
        } else {
            mInputConfirmPass.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_text_fullname:
                    validate(mEditFullName, mInputFullName, R.string.err_msg_name);
                    break;
                case R.id.input_text_email:
                    validate(mEditMail, mInputmail, R.string.err_msg_email);
                    break;
                case R.id.input_text_password:
                    validate(mEditPassword, mInputPassword, R.string.err_msg_password);
                    break;
                case R.id.input_text_confirmpassword:
                    checkPasswordConfirm();
                    break;
            }
        }
    }
}
