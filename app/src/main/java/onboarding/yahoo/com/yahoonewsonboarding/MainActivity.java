package onboarding.yahoo.com.yahoonewsonboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES=3;
    private ViewPager mPager;
    private LinearLayout mIndicatorLayout;
    private TextView mIndicatorView[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

    }

    private void setUpViews(){

        mPager=(ViewPager)findViewById(R.id.pager);
        mIndicatorLayout=(LinearLayout)findViewById(R.id.indicator_layout);

        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        setIndicatorLayout();
        setPageChangeListener(mPager);
    }

    private void setIndicatorLayout(){

        int dotsCount=NUM_PAGES;
        mIndicatorView=new TextView[dotsCount];
        for (int i = 0; i <dotsCount; i++) {

            mIndicatorView[i] = new TextView(this);
            mIndicatorView[i].setWidth(12);
            mIndicatorView[i].setHeight(12);
            mIndicatorView[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 15, 0);
            mIndicatorView[i].setLayoutParams(params);
            mIndicatorView[i].setBackgroundResource(R.drawable.rounded_cell);
            mIndicatorLayout.addView(mIndicatorView[i]);

        }

        mIndicatorView[0].setWidth(20);
        mIndicatorView[0].setHeight(20);
        mIndicatorView[0].setGravity(Gravity.CENTER);
    }

    private void setPageChangeListener(ViewPager viewPager){


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < mIndicatorView.length; i++) {
                    mIndicatorView[i].setWidth(12);
                    mIndicatorView[i].setHeight(12);
                }
                mIndicatorView[position].setWidth(20);
                mIndicatorView[position].setHeight(20);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            ScreenSlideFragment fragment=new ScreenSlideFragment();
            Bundle args=new Bundle();
            args.putInt("position",position);
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
}
