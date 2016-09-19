package com.example.framgia.imarketandroid.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

import java.util.Calendar;

/**
 * Created by toannguyen201194 on 27/07/2016.
 */
public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private final int DILOG_ID = 115;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatEdit;
    private EditText mEditFullname, mEditNumber, mEditAdress, mEditMail;
    private TextView mTextBirthday;
    private int mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener pickerListener =
        new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                mYear = selectedYear;
                mMonth = selectedMonth + 1;
                mDay = selectedDay;
                // Show selected date
                mTextBirthday.setText(String.format(Constants.FORMART_STRING, mDay, mMonth, mYear));
            }
        };
    private RelativeLayout mReContact, mReBirthday, mRePhone, mReEmail, mReCompany;
    private ImageView mImageContact, mImageBirthday, mImagePhone, mImageEmail, mImageCompany;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        initView();
        init();
        showProfile();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatEdit = (FloatingActionButton) findViewById(R.id.float_picture);
        mEditFullname = (EditText) findViewById(R.id.edit_fullname);
        mEditFullname.setOnClickListener(this);
        mEditNumber = (EditText) findViewById(R.id.edit_number);
        mEditNumber.setOnClickListener(this);
        mEditAdress = (EditText) findViewById(R.id.edit_adress);
        mEditAdress.setOnClickListener(this);
        mEditMail = (EditText) findViewById(R.id.edit_mail);
        mEditMail.setOnClickListener(this);
        mTextBirthday = (TextView) findViewById(R.id.text_birthday);
        mTextBirthday.setOnClickListener(this);
        mReContact = (RelativeLayout) findViewById(R.id.rela_contact);
        mReContact.setOnClickListener(this);
        mReBirthday = (RelativeLayout) findViewById(R.id.rela_birthday);
        mReBirthday.setOnClickListener(this);
        mRePhone = (RelativeLayout) findViewById(R.id.rela_phone_number);
        mRePhone.setOnClickListener(this);
        mReEmail = (RelativeLayout) findViewById(R.id.rela_mail);
        mReEmail.setOnClickListener(this);
        mReCompany = (RelativeLayout) findViewById(R.id.rela_company);
        mReCompany.setOnClickListener(this);
        mImageContact = (ImageView) findViewById(R.id.ivContactItem1);
        mImageBirthday = (ImageView) findViewById(R.id.ivContactItem2);
        mImagePhone = (ImageView) findViewById(R.id.ivContactItem3);
        mImageEmail = (ImageView) findViewById(R.id.ivContactItem4);
        mImageCompany = (ImageView) findViewById(R.id.ivContactItem5);
    }

    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(Constants.PROFILE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DILOG_ID:
                Calendar cal = Calendar.getInstance();
                cal.set(Constants.MINYEARPICKER, Constants.MINMONTHPICKER, Constants.MINDATEPICKER);
                cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
                cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
                cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
                cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, pickerListener,
                    mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                return datePickerDialog;
        }
        return null;
    }

    private void showProfile() {
        Session session =
            (Session) SharedPreferencesUtil.getInstance()
                .getValue(Constants.SESSION, Session.class);
        if (session != null) {
            mEditFullname.setText(session.getFullname());
            mEditMail.setText(session.getUsername());
            mEditAdress.setText(session.getAdress());
            mEditNumber.setText(session.getNumberPhone());
            mTextBirthday.setText(session.getBrithday());
        }
    }

    private void saveUpdate() {
        // TODO: 05/09/2016 send server check update
        Session session = new Session();
        session.setFullname(mEditFullname.getText().toString());
        session.setAdress(mEditAdress.getText().toString());
        session.setUsername(mEditMail.getText().toString());
        session.setNumberPhone(mEditNumber.getText().toString());
        session.setBrithday(mTextBirthday.getText().toString());
        SharedPreferencesUtil.getInstance().save(Constants.SESSION, session);
        Toast.makeText(this, R.string.toast_save_successfully, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                // TODO: 01/09/2016  event when click save 
                showEnableEditText(true);
                break;
            case R.id.action_save:
                saveUpdate();
                showProfile();
                showEnableEditText(false);
                break;
            case R.id.action_cancel:
                showProfile();
                showEnableEditText(false);
                Toast.makeText(this, R.string.toast_cancel_profile, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                SharedPreferencesUtil.getInstance().clearSharedPreference(this);
                startActivity(new Intent(UpdateProfileActivity.this, LoginActivity.class));
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEnableEditText(boolean check) {
        mTextBirthday.setEnabled(check);
        mEditAdress.setEnabled(check);
        mEditMail.setEnabled(check);
        mEditFullname.setEnabled(check);
        mFloatEdit.setEnabled(check);
        mEditNumber.setEnabled(check);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rela_contact:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageContact);
                break;
            case R.id.rela_birthday:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageBirthday);
                break;
            case R.id.rela_phone_number:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImagePhone);
                break;
            case R.id.rela_mail:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageEmail);
                break;
            case R.id.rela_company:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageCompany);
                break;
            case R.id.edit_fullname:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageContact);
                break;
            case R.id.text_birthday:
                showDialog(DILOG_ID);
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageBirthday);
                break;
            case R.id.edit_number:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImagePhone);
                break;
            case R.id.edit_mail:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageEmail);
                break;
            case R.id.edit_adress:
                DialogShareUtil.getSmallBang(UpdateProfileActivity.this, mImageCompany);
                break;
        }
    }
}