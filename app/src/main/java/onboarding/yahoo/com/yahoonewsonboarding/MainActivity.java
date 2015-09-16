package onboarding.yahoo.com.yahoonewsonboarding;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private LinearLayout mIndicatorLayout;
    private TextView mIndicatorView[];


    private ImageView mCenterBox;
    private ImageView mCamcordImage;
    private ImageView mClockImage;
    private ImageView mGraphImage;
    private ImageView mAudioImage;
    private ImageView mQuoteImage;
    private ImageView mMapImage;
    private ImageView mWordPressImage;


    private boolean mSecondPageSelected;
    private HashMap<ImageView,Float>mOriginalXValuesMap=new HashMap<>();
    private  int mSelectedPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

    }

    private void setUpViews() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        setIndicatorLayout();
        setPageChangeListener(mPager);

        //mPager.setOffscreenPageLimit(2);

    }

    private void setIndicatorLayout() {

        int dotsCount = NUM_PAGES;
        mIndicatorView = new TextView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {

            mIndicatorView[i] = new TextView(this);
            mIndicatorView[i].setWidth(12);
            mIndicatorView[i].setHeight(12);
            mIndicatorView[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 15, 0);
            mIndicatorView[i].setLayoutParams(params);
            mIndicatorView[i].setBackgroundResource(R.drawable.rounded_cell);
            mIndicatorLayout.addView(mIndicatorView[i]);

        }

        mIndicatorView[0].setWidth(20);
        mIndicatorView[0].setHeight(20);
        mIndicatorView[0].setGravity(Gravity.CENTER);
    }

    private void setPageChangeListener(ViewPager viewPager) {


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    mSelectedPosition = -1;
                    mSecondPageSelected = true;
                    setViewsInOriginalPosition();

                }
                if (position == 0) {
                    mSelectedPosition = 0;
                    doFadeAnimation();
                    //mOnSecondPageSelected=false;
                    //mOnSecondPageSelected=false;
                }

                for (int i = 0; i < mIndicatorView.length; i++) {
                    mIndicatorView[i].setWidth(12);
                    mIndicatorView[i].setHeight(12);
                }
                mIndicatorView[position].setWidth(20);
                mIndicatorView[position].setHeight(20);
            }


            @Override
            public void onPageScrollStateChanged(int state) {


                if (mSelectedPosition == 0 && state == ViewPager.SCROLL_STATE_IDLE) {

                    mSecondPageSelected = false;
                }

            }
        });

    }

    private void setViewsInOriginalPosition(){

        mCamcordImage.setX(mOriginalXValuesMap.get(mCamcordImage));
        mClockImage.setX(mOriginalXValuesMap.get(mClockImage));
        mGraphImage.setX(mOriginalXValuesMap.get(mGraphImage));
        mAudioImage.setX(mOriginalXValuesMap.get(mAudioImage));
        mQuoteImage.setX(mOriginalXValuesMap.get(mQuoteImage));
        mMapImage.setX(mOriginalXValuesMap.get(mMapImage));
        mWordPressImage.setX(mOriginalXValuesMap.get(mWordPressImage));

        initializeAlpha();

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            ScreenSlideFragment fragment = new ScreenSlideFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class CustomTransformer implements ViewPager.PageTransformer {


        @Override
        public void transformPage(View page, float position) {

            int pageWidth = page.getWidth();
            Log.d("RAHUL",""+pageWidth);

            if (position < -1) {

            } else if (position <= 1) {

                //Log.d("RAHUL", "" + (float) (-(1 - position)*0.5*pageWidth));
                //mCenterBox.setTranslationX((float) (-(1 - position) * 0.3 * pageWidth));
                if(!mSecondPageSelected) {

                    Log.d("POSITION", "" + (float) (-(1 - position) * 0.5 * pageWidth) + " " + page.toString());
                    float pos=(float) (-(1 - position) * 0.5 * pageWidth);
                    if(pos>(-1*mOriginalXValuesMap.get(mCamcordImage))) {
                        mCamcordImage.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
                    }

                }
            } else {

            }
            //Log.d("NOW POS",""+mCamcordImage.getX());
        }
    }

    public class ScreenSlideFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Bundle args = getArguments();
            int position = args.getInt("position");
            int layoutId = getLayoutId(position);


            ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId, container, false);
            if (position == 0) {

                initFirstScreenViews(rootView,savedInstanceState);
            }

            return rootView;
        }

        private int getLayoutId(int position) {

            int id = 0;
            if (position == 0) {

                id = R.layout.first_screen;

            } else if (position == 1) {

                id = R.layout.second_screen;
            } else if (position == 2) {

                id = R.layout.third_screen;
            }
            return id;
        }


    }

    private void initFirstScreenViews(View rootView, final Bundle savedInstanceState) {

        Log.d("RAHUL","CALLED");
        mCenterBox = (ImageView) rootView.findViewById(R.id.center_box);
        mCamcordImage = (ImageView) rootView.findViewById(R.id.imageView);
        mClockImage = (ImageView) rootView.findViewById(R.id.imageView6);
        mGraphImage = (ImageView) rootView.findViewById(R.id.imageView3);
        mAudioImage = (ImageView) rootView.findViewById(R.id.imageView4);
        mQuoteImage = (ImageView) rootView.findViewById(R.id.imageView5);
        mMapImage = (ImageView) rootView.findViewById(R.id.imageView2);
        mWordPressImage = (ImageView) rootView.findViewById(R.id.imageView7);

        Log.d("rag", "INITIALIXWD");

        initializeAlpha();

        rootView.post(new Runnable() {
            @Override
            public void run() {

                getOriginalXValues(savedInstanceState);

            }
        });

        if(savedInstanceState==null) {
            doFadeAnimation();
        }

    }

    private void getOriginalXValues(Bundle savedInstanceState){

        mOriginalXValuesMap.put(mCamcordImage,mCamcordImage.getX());
        mOriginalXValuesMap.put(mClockImage, mClockImage.getX());
        mOriginalXValuesMap.put(mGraphImage,mGraphImage.getX());
        mOriginalXValuesMap.put(mAudioImage,mAudioImage.getX());
        mOriginalXValuesMap.put(mQuoteImage,mQuoteImage.getX());
        mOriginalXValuesMap.put(mMapImage,mMapImage.getX());
        mOriginalXValuesMap.put(mWordPressImage, mWordPressImage.getX());

        if(savedInstanceState==null) {
            mPager.setPageTransformer(true, new CustomTransformer());
        }


    }
    private void initializeAlpha(){

        mCamcordImage.setAlpha(0f);
        mClockImage.setAlpha(0f);
        mGraphImage.setAlpha(0f);
        mAudioImage.setAlpha(0f);
        mQuoteImage.setAlpha(0f);
        mMapImage.setAlpha(0f);
        mWordPressImage.setAlpha(0f);
    }

    private void doFadeAnimation(){

         ObjectAnimator fadeCamcord = ObjectAnimator.ofFloat(mCamcordImage, "alpha", 0f, 1f);
        fadeCamcord.setDuration(700);

        ObjectAnimator fadeClock = ObjectAnimator.ofFloat(mClockImage, "alpha", 0f, 1f);
        fadeClock.setDuration(700);

        ObjectAnimator fadeGraph = ObjectAnimator.ofFloat(mGraphImage, "alpha", 0f, 1f);
        fadeGraph.setDuration(700);

        ObjectAnimator fadeAudio = ObjectAnimator.ofFloat(mAudioImage, "alpha", 0f, 1f);
        fadeAudio.setDuration(700);

        ObjectAnimator fadeQuote = ObjectAnimator.ofFloat(mQuoteImage, "alpha", 0f, 1f);
        fadeQuote.setDuration(700);

        ObjectAnimator fadeMap = ObjectAnimator.ofFloat(mMapImage, "alpha", 0f, 1f);
        fadeMap.setDuration(700);

        ObjectAnimator fadeWordpress = ObjectAnimator.ofFloat(mWordPressImage, "alpha", 0f, 1f);
        fadeWordpress.setDuration(700);

        //1 5    3 2  7 6  4

        AnimatorSet mAnimationSet = new AnimatorSet();
        fadeAudio.setStartDelay(150);
        fadeGraph.setStartDelay(300);
        fadeWordpress.setStartDelay(600);
        fadeClock.setStartDelay(900);
        fadeMap.setStartDelay(1200);
        fadeQuote.setStartDelay(1500);

        mAnimationSet.play(fadeCamcord).with(fadeAudio).with(fadeGraph).with(fadeWordpress).with(fadeClock).with(fadeMap).with(fadeQuote);


        //fadeGraph.start();

//    mAnimationSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Log.d("RAHUL","END");
//                super.onAnimationEnd(animation);
//                //mOnSecondPageSelected=false;
//            }
//
//        @Override
//        public void onAnimationCancel(Animator animation) {
//            super.onAnimationCancel(animation);
//            Log.d("RAHUL","cancel");
//        }
//    });
        mAnimationSet.start();


//        AnimatorSet mAnimationSet = new AnimatorSet();
//        mAnimationSet.play(fadeIn);
//        mAnimationSet.start();
    }
}
