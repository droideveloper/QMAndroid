package org.fs.qm.common;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.common.ZoomOutPageTransformer
 */
public class ReverseDepthPageTransformer implements ViewPager.PageTransformer {

    private final static float MIN_SCALE = .80f;

    //TODO since there is some weird movement in the end because of some weird action yet reason seems to be padding in viewPager

    @Override public void transformPage(View view, float position) {
        int width   = view.getWidth();

        if(position < -1) { //[-Infinity, -1)
            view.setAlpha(0.0f);
        }
        else if(position <= 0) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

            view.setAlpha(1 + position);//position is already negative
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            view.setTranslationX(width * -position);

//            Log.println(Log.ERROR,
//                        ReverseDepthPageTransformer.class.getSimpleName(),
//                        String.format(Locale.US, "pos: %f translateX : %f",
//                                      position, width * -position));
        }
        else if(position <= 1) {//[-1, 1]
            view.setAlpha(1.0f);
            view.setTranslationX(0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
        else { //(1, Infinity]
            view.setAlpha(0.0f);
        }
    }
}
