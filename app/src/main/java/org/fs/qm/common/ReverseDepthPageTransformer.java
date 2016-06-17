package org.fs.qm.common;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.common.ZoomOutPageTransformer
 */
public class ReverseDepthPageTransformer implements ViewPager.PageTransformer {

    private final static float MIN_SCALE = .80f;
    private final WeakReference<ViewPager>  parent;

    public ReverseDepthPageTransformer(ViewPager viewPager) {
        parent = viewPager != null ? new WeakReference<>(viewPager) : null;
    }

    //TODO padding in pager has weird movement since it not handle it in the width section

    @Override public void transformPage(View view, float position) {
        int except;
        ViewPager parent = this.parent.get();
        //will this solve my problem
        if(parent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                except = parent.getPaddingEnd() + parent.getPaddingStart();
            } else {
                except = parent.getPaddingLeft() + parent.getPaddingRight();
            }
        } else {
            except = 0;
        }

        int width   = view.getWidth() - except;

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
