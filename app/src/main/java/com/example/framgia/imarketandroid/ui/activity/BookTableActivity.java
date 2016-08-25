package com.example.framgia.imarketandroid.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;
import com.google.android.gms.internal.zzaoa;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by phongtran on 24/08/2016.
 */
public class BookTableActivity extends Activity implements View.OnClickListener {
    private Button mButtonCallTongDai;
    private Button mButtonLeftBack;
    private Button mButtonRightBack;
    private Button mButtonLeftBackKid;
    private Button mButtonRightBackKid;
    private Button mButtonLeftBackDateIn;
    private Button mButtonRightBackDateIn;
    private Button mButtonLeftBackTimeIn;
    private Button mButtonRightBackTimeIn;
    private Button mButtonLeftContinue;
    private TextView mTextViewDateIn;
    private TextView mTextViewTimeIn;
    private TextView mTextViewCount;
    private TextView mTextViewCountKid;
    private TextView mTextViewPhoneNumber;
    private EditText mEditTextNote;
    private int mCountNguoiLon = 2, mCountTreEm = 0;
    private int mNgay, mThang, mNam, mGio, mPhut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booktable_layout);
        initView();
    }

    private void initView() {
        mButtonCallTongDai = (Button) findViewById(R.id.button_call_tongdai);
        mButtonCallTongDai.setOnClickListener(this);
        mButtonLeftBack = (Button) findViewById(R.id.button_left_back);
        mButtonLeftBack.setOnClickListener(this);
        mButtonRightBack = (Button) findViewById(R.id.button_right_back);
        mButtonRightBack.setOnClickListener(this);
        mButtonLeftBackKid = (Button) findViewById(R.id.button_left_back_kid);
        mButtonLeftBackKid.setOnClickListener(this);
        mButtonRightBackKid = (Button) findViewById(R.id.button_right_back_kid);
        mButtonRightBackKid.setOnClickListener(this);
        mButtonLeftBackDateIn = (Button) findViewById(R.id.button_left_back_ngayden);
        mButtonLeftBackDateIn.setOnClickListener(this);
        mButtonRightBackDateIn = (Button) findViewById(R.id.button_right_back_ngayden);
        mButtonRightBackDateIn.setOnClickListener(this);
        mButtonLeftBackTimeIn = (Button) findViewById(R.id.button_left_back_gioden);
        mButtonLeftBackTimeIn.setOnClickListener(this);
        mButtonRightBackTimeIn = (Button) findViewById(R.id.button_right_back_gioden);
        mButtonRightBackTimeIn.setOnClickListener(this);
        mButtonLeftContinue = (Button) findViewById(R.id.button_tieptuc);
        mButtonLeftContinue.setOnClickListener(this);
        mEditTextNote = (EditText) findViewById(R.id.edit_note);
        mEditTextNote.setOnClickListener(this);
        mTextViewDateIn = (TextView) findViewById(R.id.text_ngayden);
        mTextViewDateIn.setOnClickListener(this);
        mTextViewTimeIn = (TextView) findViewById(R.id.text_gioden);
        mTextViewTimeIn.setOnClickListener(this);
        mTextViewCount = (TextView) findViewById(R.id.text_count_nguoilon);
        mTextViewCount.setOnClickListener(this);
        mTextViewCountKid = (TextView) findViewById(R.id.text_count_kid);
        mTextViewCountKid.setOnClickListener(this);
        mTextViewPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
        mTextViewPhoneNumber.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_call_tongdai:
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setPackage("com.android.server.telecom");
//                intent.setData(Uri.parse("tel:" + mTextViewPhoneNumber.getText()));
//                startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setPackage(getString(R.string.package_dial));
                intent.setData(Uri.parse(getString(R.string.tel) + mTextViewPhoneNumber.getText()));
                startActivity(intent);
                break;
            case R.id.button_left_back:
                mCountNguoiLon--;
                if (mCountNguoiLon > 0) {
                    mTextViewCount.setText(mCountNguoiLon + "");
                    mCountNguoiLon = 1;
                } else {
                    Toast.makeText(this, R.string.count_max_nguoi_lon, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_right_back:
                mCountNguoiLon++;
                mTextViewCount.setText(mCountNguoiLon + "");
                break;
            case R.id.button_left_back_kid:
                mCountTreEm--;
                if (mCountTreEm >= 0) {
                    mTextViewCountKid.setText(mCountTreEm + "");
                    mCountTreEm = 0;
                }
                break;
            case R.id.button_right_back_kid:
                mCountTreEm++;
                mTextViewCountKid.setText(mCountTreEm + "");
                break;
            case R.id.button_left_back_ngayden:
                changeDay(-1);
                break;
            case R.id.button_right_back_ngayden:
                changeDay(1);
                break;
            case R.id.button_left_back_gioden:
                changeTime(-1);
                break;
            case R.id.button_right_back_gioden:
                changeTime(1);
                break;
            case R.id.button_tieptuc:
                Intent intentt = new Intent(this, BookProductActivity.class);
                startActivity(intentt);
                finish();
                break;
            case R.id.text_ngayden:
                getShowDate();
                break;
            case R.id.text_gioden:
                getTime();
                break;
        }
    }

    private void changeTime(int i) {
        if (i == 1) {
            if (mPhut < 30) {
                mPhut += 30;
                mTextViewTimeIn.setText(mGio + Constants.COLON + mPhut);
            } else {
                mPhut -= 30;
                mGio++;
                mTextViewTimeIn.setText(mGio + Constants.COLON + mPhut);
            }
        } else {
            if (mPhut > 30) {
                mPhut -= 30;
                mTextViewTimeIn.setText(mGio + Constants.COLON + mPhut);
            } else {
                mPhut = 30 + mPhut;
                mGio--;
                mTextViewTimeIn.setText(mGio + Constants.COLON + mPhut);
            }
        }
    }

    private void changeDay(int i) {
        if (mNgay != 0) {
            if (mNgay > 1 && i == -1) {
                mNgay += i;
                mTextViewDateIn.setText(mNgay + Constants.SEPARATOR + mThang + Constants.SEPARATOR + mNam);
            } else if (mNgay == 1 && i == -1) {
                mNgay = 30;
                if (mThang > 1) {
                    mThang--;
                    mTextViewDateIn.setText(mNgay + Constants.SEPARATOR + mThang + Constants.SEPARATOR + mNam);
                } else if (mThang == 1) {
                    mThang = 12;
                    mNam--;
                    mTextViewDateIn.setText(mNgay + Constants.SEPARATOR + mThang + Constants.SEPARATOR + mNam);
                }
            } else if (mNgay < 30 && i == 1) {
                mNgay += i;
                mTextViewDateIn.setText(mNgay + Constants.SEPARATOR + mThang + Constants.SEPARATOR + mNam);
            } else if (mNgay == 30 && i == 1) {
                mNgay = 1;
                if (mThang < 12) {
                    mThang++;
                    mTextViewDateIn.setText(mNgay + Constants.SEPARATOR + mThang + Constants.SEPARATOR + mNam);
                } else if (mThang == 12) {
                    mThang = 1;
                    mNam++;
                    mTextViewDateIn.setText(mNgay + Constants.SEPARATOR + mThang + Constants.SEPARATOR + mNam);
                }
            }
        }
    }

    public void getShowDate() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            //Sự kiện khi click vào nút Done trên Dialog
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mTextViewDateIn.setText(day + Constants.SEPARATOR + (month + 1) + Constants.SEPARATOR + year);
            }
        };
        String s = mTextViewDateIn.getText() + "";
        String strArrtmp[] = s.split(Constants.SEPARATOR);
        mNgay = Integer.parseInt(strArrtmp[0]);
        mThang = Integer.parseInt(strArrtmp[1]) - 1;
        mNam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, mNam, mThang, mNgay);
        pic.setTitle(getString(R.string.select_day));
        pic.show();
    }

    public void getTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        mGio = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        mPhut = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mTextViewTimeIn.setText(selectedHour + Constants.COLON + selectedMinute);
            }
        }, mGio, mPhut, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }

}
