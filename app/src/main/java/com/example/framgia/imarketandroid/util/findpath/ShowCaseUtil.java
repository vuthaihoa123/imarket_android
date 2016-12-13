package com.example.framgia.imarketandroid.util.findpath;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.util.Constants;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by phongtran on 13/09/2016.
 */
public class ShowCaseUtil {
    public static void ShowSingleCase(Activity context, View view, String contentText, String
        showcaseId) {
        new MaterialShowcaseView.Builder(context)
            .setTarget(view)
            .setDismissText(Constants.Instruction.GOT_IT)
            .setContentText(contentText)
            .setDelay(Constants.TIME_DELAY_GUIDE)
            .singleUse(showcaseId)
            .show();
    }
}
