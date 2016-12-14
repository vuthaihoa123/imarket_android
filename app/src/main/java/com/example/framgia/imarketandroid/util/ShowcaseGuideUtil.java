package com.example.framgia.imarketandroid.util;

import android.app.Activity;
import android.content.Context;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Showcase;

import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by phongtran on 15/09/2016.
 */
public class ShowcaseGuideUtil {
    public static void singleShowcase(Activity context, String id_showcase, Showcase showcase) {
        new MaterialShowcaseView.Builder(context)
            .setTarget(showcase.getViewShowcase())
            .setDismissText(Constants.Instruction.GOT_IT)
            .setContentText(showcase.getTextShowcase())
            .setDelay(Constants.TIME_DELAY_GUIDE)
            .singleUse(id_showcase)
            .show();
    }

    public static void mutilShowcase(Activity context, String id_showcase, List<Showcase> list) {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(Constants.TIME_DELAY_GUIDE);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(context, id_showcase);
        sequence.setConfig(config);
        for (int i = 0; i < list.size(); i++) {
            sequence.addSequenceItem(list.get(i).getViewShowcase(), list.get(i).getTextShowcase(),
                Constants.Instruction.GOT_IT);
        }
        sequence.start();
    }
}
