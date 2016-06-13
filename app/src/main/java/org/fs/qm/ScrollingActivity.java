package org.fs.qm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewPager viewPager = new ViewPager(this);
//        int size = getResources().getDisplayMetrics().widthPixels;
//        viewPager.setLayoutParams(new ViewGroup.LayoutParams(size, size));
//        viewPager.setId(R.id.vpAssign);
//
//        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
//        params.width = ViewPager.LayoutParams.MATCH_PARENT;
//        params.height = ViewPager.LayoutParams.WRAP_CONTENT;
//        params.gravity =  Gravity.TOP|Gravity.CENTER_HORIZONTAL;
//
//        PagerTitleStrip strip = new PagerTitleStrip(this);
//        viewPager.addView(strip, params);
//        setContentView(R.layout.layout_main_activity);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//
//        CreateLinearProblemFragmentView frag = new CreateLinearProblemFragmentView();
//        getSupportFragmentManager().beginTransaction().replace(R.id.vgContainer, frag).commit();
//
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(!isFinishing()) {
//                        FragmentTransaction trans = getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.translate_in,      //enter
//                                             R.anim.scale_out,
//                                             R.anim.scale_in,
//                                             R.anim.translate_out)   //popExi
//                        .replace(R.id.vgContainer, new ScrollingFragment());
//
//                        trans.addToBackStack(hashCode() + "");
//                        trans.commit();
//                }
//            }
//        }, 5000);//5 secs
    }
//
//    @Override
//    public void onBackPressed() {
//        boolean popStack = getSupportFragmentManager().popBackStackImmediate();
//        if(!popStack) {
//            super.onBackPressed();
//        }
//    }
}
