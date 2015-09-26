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

    private final int FADE_INTERVAL = 500;
    private int REFRESH_INTERVAL = 10;
    private int ALPHA_INCREMENT=14;

    private boolean mFadeFirstLine = false;
    private boolean mFadeInSecondLine=false;
    private boolean mFadeInThirdLine=false;
    private boolean mFadeInFourthLine=false;

    private Paint mLinesPaintArr[]=new Paint[4];
    private FloatWrapper mFadeInLineArr[]=new FloatWrapper[4];

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

        mLine1 = BitmapFactory.decodeResource(getResources(), R.mipmap.blue_line);
        mLine2 = BitmapFactory.decodeResource(getResources(), R.mipmap.red_line);
        mLine3 = BitmapFactory.decodeResource(getResources(), R.mipmap.yellow_line);
        mLine4 = BitmapFactory.decodeResource(getResources(), R.mipmap.green_line);

        for(int i=0;i<4;i++){

            mLinesPaintArr[i]=new Paint();
            mLinesPaintArr[i].setAlpha(0);

            mFadeInLineArr[i]=new FloatWrapper(0f);

        }

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




//        Paint paint2=new Paint();
//        paint2.setAlpha(0);
//
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm, 10, 40, paint);
//
//        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm2, 10, 110, paint);
//
//        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm3,10,190,paint);
//
//        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.mipmap.lines);
//        canvas.drawBitmap(bm4,10,260,paint);




        Log.d("ALPHA", "" + mLinesPaint.getAlpha());

        if (mFadeInLineArr[0].floatValue==1f) {

            canvas.drawBitmap(mLine1, 10, 40, mLinesPaintArr[0]);
            fadeTheLine(mLinesPaintArr[0], mFadeInLineArr[1], mFadeInLineArr[0]);

        }

        if (mFadeInLineArr[1].floatValue==1f) {

            canvas.drawBitmap(mLine1,10,40,null);
            canvas.drawBitmap(mLine2, 10, 110, mLinesPaintArr[1]);

            fadeTheLine(mLinesPaintArr[1],mFadeInLineArr[2],mFadeInLineArr[1]);

        }

        if (mFadeInLineArr[2].floatValue==1f) {

            canvas.drawBitmap(mLine1,10,40,null);
            canvas.drawBitmap(mLine2, 10, 110, null);
            canvas.drawBitmap(mLine3, 10, 180, mLinesPaintArr[2]);

            fadeTheLine(mLinesPaintArr[2],mFadeInLineArr[3],mFadeInLineArr[2]);

        }

        if (mFadeInLineArr[3].floatValue==1f) {

            canvas.drawBitmap(mLine1,10,40,null);
            canvas.drawBitmap(mLine2, 10, 110, null);
            canvas.drawBitmap(mLine3, 10, 180, null);
            canvas.drawBitmap(mLine4, 10, 250, mLinesPaintArr[3]);

            fadeTheLine(mLinesPaintArr[3],mFadeInLineArr[3],mFadeInLineArr[3]);

        }







//        if(!mFadeFirstLine) {
//            mDummyPaint.setStyle(Paint.Style.FILL);
//            mDummyPaint.setColor(Color.WHITE);
//            canvas.drawRect(5, 25, 215, 325, mDummyPaint);
//        }
//
//
//        if(mFadeFirstLine){
//
//            if(mSlidingDistance>=200){
//                mFadeFirstLine=false;
//                return;
//            }
//            mSlidingDistance+=0.8;
//            canvas.drawRect(5,25+mSlidingDistance,215,325,mDummyPaint);
//            //postInvalidateDelayed(1);
//            invalidate();
//        }


    }

    private void fadeTheLine(Paint paint,FloatWrapper nextLine,FloatWrapper currentLine){

        if (paint.getAlpha() != 255) {
            //int newAlpha = mLinesPaint.getAlpha() + (int) (255 * ((REFRESH_INTERVAL * 1.0) / FADE_INTERVAL));
            int newAlpha=paint.getAlpha()+ALPHA_INCREMENT;
            if (newAlpha >= 255) {
                newAlpha = 255;
            }

            paint.setAlpha(newAlpha);
            //postInvalidateDelayed(REFRESH_INTERVAL);
            invalidate();
        }
        else{
            paint.setAlpha(0);
            nextLine.floatValue=1f;
            currentLine.floatValue=0f;
        }


    }


    private void fadeInFirstLine(Canvas canvas,Bitmap bitmap){


    }




    public void fadeInTheLines() {

        mFadeInLineArr[0].floatValue = 1f;
        mFadeInLineArr[1].floatValue = 0f;
        mFadeInLineArr[2].floatValue = 0f;
        mFadeInLineArr[3].floatValue = 0f;



        invalidate();



    }

}