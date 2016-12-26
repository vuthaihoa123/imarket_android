package com.example.framgia.imarketandroid.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.ConvertImageToBase64Util;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.Flog;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

import java.io.FileNotFoundException;
import java.util.Calendar;

/**
 * Created by toannguyen201194 on 27/07/2016.
 */
public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private final int DILOG_ID = 115;
    private final int WIDTHIMAGE = 800;
    private final int HEIGHTIMAGE = 600;
    Bitmap bitmap;
    Uri uri;
    private Toolbar mToolbar;
    private View mView;
    private ImageView mImageAvatar;
    private FloatingActionButton mFloatEdit;
    private EditText mEditFullname, mEditNumber, mEditAdress, mEditMail;
    private TextView mTextBirthday;
    private ProgressDialog mProgressDialog;
    private int mYear, mMonth, mDay;
    private int mId;
    Session mSession = new Session();
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
    private String mAuthToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        initView();
        init();
        UserModel userModel =
            (UserModel) SharedPreferencesUtil.getInstance()
                .getValue(Constants.SESSION, UserModel.class);
        if (userModel.getSession() != null) {
            if (userModel.getSession().getmAuthToken() != null) {
                mAuthToken = userModel.getSession().getmAuthToken();
            }
            HttpRequest.getInstance(getBaseContext()).initAuthToken(mAuthToken);
        }
        showProfile();
        showEnableEditText(false);
    }

    private void initView() {
        mView = findViewById(android.R.id.content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImageAvatar = (ImageView) findViewById(R.id.image_avatar);
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
        mFloatEdit = (FloatingActionButton) findViewById(R.id.float_picture);
        mFloatEdit.setOnClickListener(this);
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
        UserModel userModel =
            (UserModel) SharedPreferencesUtil.getInstance()
                .getValue(Constants.SESSION, UserModel.class);
        if (userModel != null && userModel.getSession() != null) {
            if (userModel.getSession().getUrlImage() != null) {
                String url = Constants.HEAD_URL + userModel.getSession().getUrlImage();
                Glide.with(getBaseContext()).load(url)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.game_icon)
                    .into(mImageAvatar);
            }
            mId = userModel.getSession().getId();
            mEditFullname.setText(userModel.getSession().getFullname());
            mEditMail.setText(userModel.getSession().getUsername());
            mEditAdress.setText(userModel.getSession().getAdress());
            mEditNumber.setText(userModel.getSession().getNumberPhone());
            mTextBirthday.setText(userModel.getSession().getBrithday());
        }
    }

    private void saveUpdate() {
        // TODO: 05/09/2016 send server check update
        mSession.setId(mId);
        mSession.setFullname(mEditFullname.getText().toString());
        mSession.setAdress(mEditAdress.getText().toString());
        mSession.setUsername(mEditMail.getText().toString());
        mSession.setNumberPhone(mEditNumber.getText().toString());
        mSession.setBrithday(mTextBirthday.getText().toString());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progressdialog));
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        HttpRequest.getInstance(getBaseContext()).updateUser(mId, new UserModel(mSession));
        HttpRequest.getInstance(getBaseContext())
            .setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
                @Override
                public void onLoadDataSuccess(Object object) {
                    UserModel userModel1 = (UserModel) object;
                    mProgressDialog.dismiss();
                    if (userModel1.getSession() != null) {
                        Flog.toast(getBaseContext(), R.string.toast_save_successfully);
                        SharedPreferencesUtil.getInstance().clearSharedPreference();
                        SharedPreferencesUtil.getInstance().save(Constants.SESSION, userModel1);
                        String url = Constants.HEAD_URL + userModel1.getSession().getUrlImage();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(UpdateProfileActivity.this).clearDiskCache();
                            }
                        }).start();
                        Glide.get(UpdateProfileActivity.this).clearMemory();
                        Glide.with(getBaseContext()).load(url)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(false)
                            .centerCrop()
                            .into(mImageAvatar);
                    } else {
                        Flog.toast(getBaseContext(), userModel1.getmErrors().toString());
                    }
                }

                @Override
                public void onLoadDataFailure(String message) {
                    mProgressDialog.dismiss();
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                showEnableEditText(true);
                break;
            case R.id.action_save:
                saveUpdate();
                showEnableEditText(false);
                break;
            case R.id.action_cancel:
                showProfile();
                showEnableEditText(false);
                Flog.toast(this, R.string.toast_cancel_profile);
                break;
            case R.id.action_logout:
                SharedPreferencesUtil.getInstance().clearSharedPreference();
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
        if (check) {
            mFloatEdit.setVisibility(View.VISIBLE);
        } else {
            mFloatEdit.setVisibility(View.GONE);
        }
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
            case R.id.float_picture:
                DialogShareUtil.startDilog(UpdateProfileActivity.this, mView);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uri = data.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    try {
                        BitmapFactory
                            .decodeStream(getContentResolver().openInputStream(uri), null, options);
                        options.inSampleSize = ConvertImageToBase64Util.calculateInSampleSize
                            (options, WIDTHIMAGE, HEIGHTIMAGE);
                        options.inJustDecodeBounds = false;
                        Bitmap image = BitmapFactory
                            .decodeStream(getContentResolver().openInputStream(uri), null, options);
                        String imAvater = ConvertImageToBase64Util.encodeToBase64(image, 100);
                        mSession.setUrlImage(imAvater);
                        mImageAvatar.setImageBitmap(ConvertImageToBase64Util.decodeBase64
                            (imAvater));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Flog.toast(this, R.string.cancelled);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Flog.toast(this, R.string.cancelled);
            }
        } else if (requestCode == Constants.CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(Constants.BUNDLE_DATA)) {
                    bitmap = (Bitmap) data.getExtras().get(Constants.BUNDLE_DATA);
                    String imAvater = ConvertImageToBase64Util.encodeToBase64(bitmap, 100);
                    mSession.setUrlImage(imAvater);
                    mImageAvatar.setImageBitmap(ConvertImageToBase64Util.decodeBase64(imAvater));
                } else if (data.getExtras() == null) {
                    Flog.toast(this, R.string.toast_camerarequest);
                    BitmapDrawable thumbnail = new BitmapDrawable(
                        getResources(), data.getData().getPath());
                    mImageAvatar.setImageDrawable(thumbnail);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Flog.toast(this, R.string.cancelled);
            }
        }
    }
}
