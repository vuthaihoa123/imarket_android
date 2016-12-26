package com.example.framgia.imarketandroid.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;

/**
 * Created by vutha on 10/4/2016.
 */
public class ScreenSlidePageFragment extends Fragment {
    private ImageView mIvShowPreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        mIvShowPreview = (ImageView) getView().findViewById(R.id.iv_show_preview);
        Bundle returnedBundle = getArguments();
        if (returnedBundle != null) {
            int idRes = returnedBundle.getInt(Constants.EXTRA_ID_RES_PREVIEW_DETAILS);
            mIvShowPreview.setImageResource(idRes);
        }
    }
}
