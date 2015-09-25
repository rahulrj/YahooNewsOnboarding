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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    private AnimatorSet mAnimatorSet;

    private TextView mTitleText;
    private TextView mDescText;


    private boolean mSecondPageSelected;
    private HashMap<ImageView, Float> mOriginalXValuesMap = new HashMap<>();
    private int mSelectedPosition = -1;

    //Second screen
    private AnimationView mAnimationView;
    private float mPreviousPositionOffset;
    private boolean mViewPagerScrollingLeft;
    private int mPreviousPosition;
    private BookView mBookView;


    // Third screen
    private boolean mShouldSpheresRotate=true;
    private ThirdScreenView mRoundView;
    private boolean mThirdPageSelected;
    private Button mLetsGoButton;

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

            int oldPos = mPager.getCurrentItem();

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


//                // Log.d("RAHUL",""+position+" "+positionOffset);
//                if (positionOffset > mPreviousPositionOffset || (mPreviousPositionOffset>0.5f && positionOffset==0)) {
//                    //Log.d("RAHUL","LEFT");
//                    mViewPagerScrollingLeft = true;
//                }
//                if (positionOffset < mPreviousPositionOffset ||(mPreviousPositionOffset==0f && positionOffset>0.5f)) {
//                    //Log.d("RAHUL","RIGHT");
//                    mViewPagerScrollingLeft = false;
//
//                }

                if ((positionOffset > mPreviousPositionOffset && position == mPreviousPosition) || (positionOffset < mPreviousPositionOffset && position > mPreviousPosition)) {
                    mViewPagerScrollingLeft = true;
                } else if (positionOffset < mPreviousPositionOffset) {

                    mViewPagerScrollingLeft = false;
                }
                mPreviousPositionOffset = positionOffset;
                mPreviousPosition = position;

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    mSelectedPosition = 1;
                    mSecondPageSelected = true;
                    setViewsInOriginalPosition();
                    if (mAnimatorSet != null) {
                        mAnimatorSet.cancel();
                    }

                    animateBookView();
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

                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    mShouldSpheresRotate = false;
                    //Log.d("DRAG","DRAGGING");
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mShouldSpheresRotate = true;
                    //Log.d("DRAG","NOT DRAGGING");
                }
                if (mRoundView != null) {
                    mRoundView.setRotatingPermission(mShouldSpheresRotate);
                }

                if (mSelectedPosition == 0 && state == ViewPager.SCROLL_STATE_IDLE) {

                    mSecondPageSelected = false;
                }

            }
        });

    }

    private void animateBookView(){

        mBookView.fadeInTheLines();
    }

    private void setViewsInOriginalPosition() {

        mCenterBox.setX(mOriginalXValuesMap.get(mCenterBox));
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
            //Log.d("RAHUL", "" + pageWidth);

            if((mViewPagerScrollingLeft && page.findViewById(R.id.center_box)!=null)) {
                animateSecondScreen(position, pageWidth, 0);
                //Log.d("RAHUL","ANIMTE CLOCK");
            }

            if(!mViewPagerScrollingLeft && page.findViewById(R.id.center_box_second)!=null){

                //Log.d("RAHUL","ANIMTE ANTI-CLOCK");
                animateSecondScreen(position, pageWidth, 1);
            }


            if (position < -1) {

            } else if (position <= 1) {

                if (!mSecondPageSelected) {

                    moveTheSpheres(position, pageWidth);

                }

                if(!mShouldSpheresRotate && page.findViewById(R.id.center_box_third)!=null){

                    mRoundView.translateTheSpheres(position,pageWidth);
                }


            } else {

            }
            //Log.d("NOW POS",""+mCamcordImage.getX());
        }
    }

    private void moveTheSpheres(float position, int pageWidth) {

        //Log.d("POSITION", "" + (float) (-(1 - position) * 0.5 * pageWidth));

//        float centerBoxPos = (float) (-(1 - position) * 0.80 * pageWidth);
//        if (centerBoxPos > (-1 * mOriginalXValuesMap.get(mCenterBox))) {
//            mCenterBox.setTranslationX(centerBoxPos);
//        }

        float camcordPos = (float) ((1 - position) * 0.15 * pageWidth);
        if (camcordPos > (-1 * mOriginalXValuesMap.get(mCamcordImage))) {
            mCamcordImage.setTranslationX(camcordPos);
        }

        //mCamcordImage.setTranslationX(1.5f);
        Log.d("RAHUL",""+mCamcordImage.getX()+" "+mCenterBox.getX());

        float clockPos = (float) ((1 - position) * 0.50 * pageWidth);
        if (clockPos > (-1 * mOriginalXValuesMap.get(mClockImage))) {
            mClockImage.setTranslationX(clockPos);
        }

        float graphPos = (float) ((1 - position) * 0.30 * pageWidth);
        if (graphPos > (-1 * mOriginalXValuesMap.get(mGraphImage))) {
            mGraphImage.setTranslationX(graphPos);
        }

        float audioPos = (float) ((1 - position) * 0.30 * pageWidth);
        if (audioPos > (-1 * mOriginalXValuesMap.get(mAudioImage))) {
            mAudioImage.setTranslationX(audioPos);
        }


        float quotePos = (float) (-(1 - position) * 0.37 * pageWidth);
        if (quotePos > (-1 * mOriginalXValuesMap.get(mQuoteImage))) {
            mQuoteImage.setTranslationX(quotePos);
        }

        float mapPos = (float) (-(1 - position) * 1.1 * pageWidth);
        if (mapPos > (-1 * mOriginalXValuesMap.get(mMapImage))) {
            mMapImage.setTranslationX(mapPos);
        }

        float wordpressPos = (float) (-(1 - position) * 0.37 * pageWidth);
        if (wordpressPos > (-1 * mOriginalXValuesMap.get(mWordPressImage))) {
            mWordPressImage.setTranslationX(wordpressPos);
        }



    }

    private void animateSecondScreen(float position, int pageWidth,int direction){

        if(direction==0) {
            mAnimationView.animateSecondScreenClock(position);
        }
        else{
            mAnimationView.animateSecondScreenAntiClock(position);
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

                initFirstScreenViews(rootView, savedInstanceState);
            }
            if(position==1){

                initSecondScreenViews(rootView, savedInstanceState);
            }
            if(position==2){

                initThirdScreenViews(rootView,savedInstanceState);
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

        Log.d("RAHUL", "CALLED");
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

        if (savedInstanceState == null) {
            doFadeAnimation();
        }

    }

    private void getOriginalXValues(Bundle savedInstanceState) {

        mOriginalXValuesMap.put(mCenterBox, mCenterBox.getX());
        mOriginalXValuesMap.put(mCamcordImage, mCamcordImage.getX());
        mOriginalXValuesMap.put(mClockImage, mClockImage.getX());
        mOriginalXValuesMap.put(mGraphImage, mGraphImage.getX());
        mOriginalXValuesMap.put(mAudioImage, mAudioImage.getX());
        mOriginalXValuesMap.put(mQuoteImage, mQuoteImage.getX());
        mOriginalXValuesMap.put(mMapImage, mMapImage.getX());
        mOriginalXValuesMap.put(mWordPressImage, mWordPressImage.getX());

        if (savedInstanceState == null) {
            mPager.setPageTransformer(true, new CustomTransformer());
        }


    }

    private void initializeAlpha() {

        mCamcordImage.setAlpha(0f);
        mClockImage.setAlpha(0f);
        mGraphImage.setAlpha(0f);
        mAudioImage.setAlpha(0f);
        mQuoteImage.setAlpha(0f);
        mMapImage.setAlpha(0f);
        mWordPressImage.setAlpha(0f);
    }

    private void doFadeAnimation() {



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

        mAnimatorSet = new AnimatorSet();
        fadeAudio.setStartDelay(150);
        fadeGraph.setStartDelay(300);
        fadeWordpress.setStartDelay(600);
        fadeClock.setStartDelay(900);
        fadeMap.setStartDelay(1200);
        fadeQuote.setStartDelay(1500);

        mAnimatorSet.play(fadeCamcord).with(fadeAudio).with(fadeGraph).with(fadeWordpress).with(fadeClock).with(fadeMap).with(fadeQuote);
        mAnimatorSet.start();




//        AnimatorSet mAnimationSet = new AnimatorSet();
//        mAnimationSet.play(fadeIn);
//        mAnimationSet.start();
    }

    private void initSecondScreenViews(View rootView,Bundle savedInstanceState){

        final RelativeLayout secondScreenRoot=(RelativeLayout)rootView.findViewById(R.id.root);
        //final ImageView centerBox=(ImageView)rootView.findViewById(R.id.center_box_second);
        mBookView=(BookView)rootView.findViewById(R.id.center_box_second);
        mAnimationView=(AnimationView)rootView.findViewById(R.id.animation_view);





    }

    private void initThirdScreenViews(View rootView,Bundle savedInstanceState){

        mRoundView=(ThirdScreenView)rootView.findViewById(R.id.round_view);
        mLetsGoButton=(Button)rootView.findViewById(R.id.letsgo);

        mLetsGoButton.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.letsgo:

                    mRoundView.startNextScreen();

                    break;
            }
        }
    };

//    private TextView sampleView;
//    private void drawCircle(ImageView drawingView,ImageView centerBox,RelativeLayout rootView){
//
//        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
//                .getDefaultDisplay().getWidth(), (int) getWindowManager()
//                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawingView.setImageBitmap(bitmap);
//
//        Paint p = new Paint();
//        p.setColor(Color.BLACK);
//        DashPathEffect dashPath = new DashPathEffect(new float[]{7,7}, (float)1.0);
//
//        p.setPathEffect(dashPath);
//        p.setStyle(Paint.Style.STROKE);
//
//        sampleView=new TextView(MainActivity.this);
//        sampleView.setX(320);
//        sampleView.setY(40);
//        sampleView.setText("Rahul");
//        rootView.addView(sampleView);
//
//
//
//        Log.d("MEASURE",centerBox.getX()+centerBox.getWidth()/2+" "+centerBox.getY()+centerBox.getHeight()/2+80);
//        canvas.drawCircle(centerBox.getX()+centerBox.getWidth()/2, centerBox.getY()+centerBox.getHeight()/2+80, 360, p);
//    }
}
