package onboarding.yahoo.com.yahoonewsonboarding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by rahul.raja on 9/23/15.
 */
public class BookView extends View {

    Paint paint;
    Paint mDummyPaint;
    private Paint mLinesPaint;
    private Bitmap mLine1, mLine2, mLine3, mLine4;
    private int mSlidingDistance = 0;

    private final int FADE_INTERVAL = 700;
    private int REFRESH_INTERVAL = 10;

    private boolean mFadeFirstLine = false;
    private boolean mFadeInSecondLine=false;
    private boolean mFadeInThirdLine=false;
    private boolean mFadeInFourthLine=false;

    public BookView(Context context) {
        super(context);
        initMyView();
    }

    public BookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public BookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView();
    }


    public void initMyView() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        mLinesPaint = new Paint();
        mDummyPaint = new Paint();
        mLinesPaint.setAlpha(0);

        mLine1 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
        mLine2 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
        mLine3 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
        mLine4 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);

//        paint2 = new Paint();
//        paint2.setColor(Color.WHITE);
//        paint2.setStrokeWidth(2);
//        paint2.setStyle(Paint.Style.FILL);
//        paint2.setAlpha(0);


        //bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//        bm_offsetX = bm.getWidth()/2;
//        bm_offsetY = bm.getHeight()/2;

//        animPath = new Path();
//        animPath.moveTo(5, 5);
//
//        animPath.lineTo(230, 5);
//        animPath.lineTo(230, 460);
//        animPath.rLineTo(-50,0);
//
//        //animPath.close();
//
//        pathMeasure = new PathMeasure(animPath, false);
//        pathLength = pathMeasure.getLength();
//
//        Toast.makeText(getContext(), "pathLength: " + pathLength, Toast.LENGTH_LONG).show();
//
//        step = 1;
//        distance = 0;
//        pos = new float[2];
//        tan = new float[2];
//
//        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        //canvas.drawPath(animPath, paint);
        canvas.drawRect(10, 10, 230, 320, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 20, 220, 330, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(1, 20, 220, 330, paint);


//
//
//        Paint paint2=new Paint();
//        paint2.setAlpha(0);
//
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm, 10, 40, null);
//
//        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm2, 10, 110, null);
//
//        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm3,10,190,null);
//
//        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm4,10,260,null);


        Log.d("ALPHA",""+mLinesPaint.getAlpha());

        if (mFadeFirstLine) {

            canvas.drawBitmap(mLine1, 10, 40, mLinesPaint);

            if (mLinesPaint.getAlpha() != 255) {
                int newAlpha = mLinesPaint.getAlpha() + (int) (255 * ((REFRESH_INTERVAL * 1.0) / FADE_INTERVAL));
                if (newAlpha >= 255) {
                    newAlpha = 255;
                }

                mLinesPaint.setAlpha(newAlpha);
                postInvalidateDelayed(REFRESH_INTERVAL);
            }
            else{
                mLinesPaint.setAlpha(0);
                mFadeInSecondLine=true;
                mFadeFirstLine=false;
            }
        }

        if (mFadeInSecondLine) {

            canvas.drawBitmap(mLine1,10,40,null);
            canvas.drawBitmap(mLine2, 10, 110, mLinesPaint);

            if (mLinesPaint.getAlpha() != 255) {
                int newAlpha = mLinesPaint.getAlpha() + (int) (255 * ((REFRESH_INTERVAL * 1.0) / FADE_INTERVAL));
                if (newAlpha >= 255) {
                    newAlpha = 255;
                }

                mLinesPaint.setAlpha(newAlpha);
                postInvalidateDelayed(REFRESH_INTERVAL);
            }
            else{
                mLinesPaint.setAlpha(0);
                mFadeInThirdLine=true;
                mFadeInSecondLine=false;
            }
        }

        if (mFadeInThirdLine) {

            canvas.drawBitmap(mLine1,10,40,null);
            canvas.drawBitmap(mLine2, 10, 110, null);

            canvas.drawBitmap(mLine3, 10, 190, mLinesPaint);
            if (mLinesPaint.getAlpha() != 255) {
                int newAlpha = mLinesPaint.getAlpha() + (int) (255 * ((REFRESH_INTERVAL * 1.0) / FADE_INTERVAL));
                if (newAlpha >= 255) {
                    newAlpha = 255;
                }

                mLinesPaint.setAlpha(newAlpha);
                postInvalidateDelayed(REFRESH_INTERVAL);
            }
            else{
                mLinesPaint.setAlpha(0);
                mFadeInFourthLine=true;
                mFadeInThirdLine=false;
            }
        }

        if (mFadeInFourthLine) {

            canvas.drawBitmap(mLine1,10,40,null);
            canvas.drawBitmap(mLine2, 10, 110, null);
            canvas.drawBitmap(mLine3, 10, 190, null);

            canvas.drawBitmap(mLine4, 10, 260, mLinesPaint);
            if (mLinesPaint.getAlpha() != 255) {
                int newAlpha = mLinesPaint.getAlpha() + (int) (255 * ((REFRESH_INTERVAL * 1.0) / FADE_INTERVAL));
                if (newAlpha >= 255) {
                    newAlpha = 255;
                }

                mLinesPaint.setAlpha(newAlpha);
                postInvalidateDelayed(REFRESH_INTERVAL);
            }
            else{
                mLinesPaint.setAlpha(0);
                mFadeInFourthLine=false;
            }
        }







//        if(!mFadeInFirstLine) {
//            mDummyPaint.setStyle(Paint.Style.FILL);
//            mDummyPaint.setColor(Color.WHITE);
//            canvas.drawRect(5, 25, 215, 325, mDummyPaint);
//        }


//        if(mFadeInFirstLine){
//
//            if(mSlidingDistance>=200){
//                mFadeInFirstLine=false;
//                return;
//            }
//            mSlidingDistance+=0.8;
//            canvas.drawRect(5,25+mSlidingDistance,215,325,mDummyPaint);
//            postInvalidateDelayed(1);
//            //invalidate();
//        }


    }

    private void fadeTheLine(Canvas canvas,Bitmap bitmap){



    }


    private void fadeInFirstLine(Canvas canvas,Bitmap bitmap){


    }




    public void fadeInTheLines() {

        mFadeFirstLine = true;
        invalidate();
    }

}