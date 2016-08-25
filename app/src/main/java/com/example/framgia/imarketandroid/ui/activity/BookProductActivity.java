package com.example.framgia.imarketandroid.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.Calendar;

/**
 * Created by phongtran on 25/08/2016.
 */
public class BookProductActivity extends Activity implements View.OnClickListener {
    private static final int SHIP = 1;
    private static final int GOTOSHOP = 2;

    private EditText mEditTextEmail, mEditTextAddress, mEditTextPhoneNumber;
    private Button mButtonClearEmail, mButtonClearAddress, mButtonClearPhoneNumber;
    private TextView mTextViewTimeShip, mTextViewDateShip;
    private TextView mTextViewTimeGoToShop, mTextViewDateGoToShop;
    private TextView mTextViewForgetPass;
    private Button mButtonContinue;
    private RadioButton mRadioButtonOnline, mRadioButtonOffline;
    private LinearLayout mLinearLayoutRadioOnline, mLinearLayoutRadioOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookproduct_layout);
        initView();
    }

    private void initView() {
        mLinearLayoutRadioOnline = (LinearLayout) findViewById(R.id.linear_muaonline);
        mLinearLayoutRadioOnline.setVisibility(View.GONE);
        mLinearLayoutRadioOffline = (LinearLayout) findViewById(R.id.linear_muataicuahang);
        mLinearLayoutRadioOffline.setVisibility(View.GONE);
        mEditTextEmail = (EditText) findViewById(R.id.edit_email_book_product);
        mEditTextPhoneNumber = (EditText) findViewById(R.id.edit_phone_book_product);
        mEditTextAddress = (EditText) findViewById(R.id.edit_address_ship);
        mButtonClearEmail = (Button) findViewById(R.id.button_clear_email);
        mButtonClearEmail.setOnClickListener(this);
        mButtonClearPhoneNumber = (Button) findViewById(R.id.button_clear_phonenumber);
        mButtonClearPhoneNumber.setOnClickListener(this);
        mButtonClearAddress = (Button) findViewById(R.id.button_clear_address_ship);
        mButtonClearAddress.setOnClickListener(this);
        mTextViewTimeShip = (TextView) findViewById(R.id.text_gio_ship);
        mTextViewTimeShip.setOnClickListener(this);
        mTextViewDateShip = (TextView) findViewById(R.id.text_ngay_ship);
        mTextViewDateShip.setOnClickListener(this);
        mTextViewForgetPass = (TextView) findViewById(R.id.text_forget_password);
        mTextViewForgetPass.setOnClickListener(this);
        mButtonContinue = (Button) findViewById(R.id.button_continue_book_product);
        mButtonContinue.setOnClickListener(this);
        mRadioButtonOnline = (RadioButton) findViewById(R.id.radioButtonOnline);
        mRadioButtonOnline.setOnClickListener(this);
        mRadioButtonOffline = (RadioButton) findViewById(R.id.radioButtonOff);
        mRadioButtonOffline.setOnClickListener(this);
        mTextViewTimeGoToShop = (TextView) findViewById(R.id.text_gioden);
        mTextViewTimeGoToShop.setOnClickListener(this);
        mTextViewDateGoToShop = (TextView) findViewById(R.id.text_ngay_den);
        mTextViewDateGoToShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_email:
                mEditTextEmail.setText("");
                break;
            case R.id.button_clear_phonenumber:
                mEditTextPhoneNumber.setText("");
                break;
            case R.id.button_clear_address_ship:
                mEditTextAddress.setText("");
                break;
            case R.id.text_gio_ship:
                getTime(SHIP);
                break;
            case R.id.text_ngay_ship:
                getShowDate(SHIP);
                break;
            case R.id.text_forget_password:
                // TODO forget pass word
                break;
            case R.id.button_continue_book_product:
                // TODO continue
                Intent intent = new Intent(this, BookTableActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.radioButtonOnline:
                mLinearLayoutRadioOnline.setVisibility(View.VISIBLE);
                mLinearLayoutRadioOffline.setVisibility(View.GONE);
                mRadioButtonOffline.setChecked(false);
                break;
            case R.id.radioButtonOff:
                mLinearLayoutRadioOnline.setVisibility(View.GONE);
                mLinearLayoutRadioOffline.setVisibility(View.VISIBLE);
                mRadioButtonOnline.setChecked(false);
                break;
            case R.id.text_gioden:
                getTime(GOTOSHOP);
                break;
            case R.id.text_ngay_den:
                getShowDate(GOTOSHOP);
                break;
        }
    }

    public void getShowDate(final int i) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if (i == SHIP) {
                    mTextViewDateShip.setText(day + Constants.SEPARATOR + (month + 1) + Constants.SEPARATOR + year);
                } else if (i == GOTOSHOP) {
                    mTextViewDateGoToShop.setText(day + Constants.SEPARATOR + (month + 1) + Constants.SEPARATOR + year);
                }
            }
        };
        if (i == SHIP) {
            String s = mTextViewDateShip.getText() + "";
            String strArrtmp[] = s.split(Constants.SEPARATOR);
            int ngay = Integer.parseInt(strArrtmp[0]);
            int thang = Integer.parseInt(strArrtmp[1]) - 1;
            int nam = Integer.parseInt(strArrtmp[2]);
            DatePickerDialog pic = new DatePickerDialog(
                    this,
                    callback, nam, thang, ngay);
            pic.setTitle(getString(R.string.select_day));
            pic.show();
        } else if (i == GOTOSHOP) {
            String s = mTextViewDateGoToShop.getText() + "";
            String strArrtmp[] = s.split(Constants.SEPARATOR);
            int ngay = Integer.parseInt(strArrtmp[0]);
            int thang = Integer.parseInt(strArrtmp[1]) - 1;
            int nam = Integer.parseInt(strArrtmp[2]);
            DatePickerDialog pic = new DatePickerDialog(
                    this,
                    callback, nam, thang, ngay);
            pic.setTitle(getString(R.string.select_day));
            pic.show();
        }
    }

    public void getTime(final int i) {
        Calendar mcurrentTime = Calendar.getInstance();
        int gio = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int phut = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (i == SHIP) {
                    mTextViewTimeShip.setText(selectedHour + Constants.COLON + selectedMinute);
                } else if (i == GOTOSHOP) {
                    mTextViewTimeGoToShop.setText(selectedHour + Constants.COLON + selectedMinute);
                }
            }
        }, gio, phut, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }


}
