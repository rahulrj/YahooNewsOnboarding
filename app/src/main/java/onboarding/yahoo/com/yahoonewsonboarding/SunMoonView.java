package onboarding.yahoo.com.yahoonewsonboarding;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;

/**
 * Created by rahul.raja on 9/19/15.
 */
public class SunMoonView extends View {

    private Paint mPaint;
    private Bitmap mBitmapSun;
    private int mBm_offsetX, mBm_offsetY;
    private int mBm_offsetX2, mBm_offsetY2;

    private Path mAnimPath;
    private PathMeasure mPathMeasure;
    private float mPathLength;

    private float mStep;   //distance each step
    private float mDistance;  //distance moved

    private float[] mPos;
    private float[] mTan;
    private Matrix mMatrix;

    private Bitmap mBitmapMoon;
    private PathMeasure mPathMeasureMoon;

    private float RADIUS;
    private float XCOORD;
    private float YCOORD;


    Path.Direction mCurrentDirection = Path.Direction.CW;

    public SunMoonView(Context context) {
        super(context);
        initMyView(context);
    }

    public SunMoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView(context);
    }

    public SunMoonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView(context);
    }

    public void initMyView(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        DashPathEffect dashPath = new DashPathEffect(new float[]{4, 5}, (float) 1.0);

        mPaint.setPathEffect(dashPath);

        mBitmapSun = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        mBitmapMoon = BitmapFactory.decodeResource(getResources(), R.mipmap.moon_new);
        mBm_offsetX = mBitmapSun.getWidth() / 2;
        mBm_offsetY = mBitmapSun.getHeight() / 2;

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        XCOORD=size.x/2;
        YCOORD=(float)(size.y*0.28);
        RADIUS=(float)(7*1.0/18)*size.x;


        mBm_offsetX2 = mBitmapMoon.getWidth() / 2;
        mBm_offsetY2 = mBitmapMoon.getHeight() / 2;

        initNewPath(Path.Direction.CW);

        mStep = 1;
        mDistance = 0;
        mPos = new float[2];
        mTan = new float[2];
        mMatrix = new Matrix();

    }

    private void initNewPath(Path.Direction dir) {

        mAnimPath = new Path();
        RectF rectF = new RectF(XCOORD - RADIUS, YCOORD - RADIUS, XCOORD + RADIUS, YCOORD + RADIUS);

        if (dir == Path.Direction.CW) {
            mAnimPath.addArc(rectF, 50, 359);
        } else {

            mAnimPath.addArc(rectF, 50, -359);
        }

        mAnimPath.close();

        mPathMeasure = new PathMeasure(mAnimPath, false);
        mPathMeasureMoon = new PathMeasure(mAnimPath, false);
        mPathLength = mPathMeasure.getLength();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(mAnimPath, mPaint);

        if (mDistance < mPathLength) {
            mPathMeasure.getPosTan(mDistance, mPos, mTan);

            mMatrix.reset();
            mMatrix.postTranslate(mPos[0] - mBm_offsetX, mPos[1] - mBm_offsetY);
            canvas.drawBitmap(mBitmapSun, mMatrix, null);

        } else {
            mDistance = 0;
        }


        if (mDistance < mPathLength) {

            mMatrix.reset();
            if (mDistance + mPathLength / 2 <= mPathLength) {
                mPathMeasure.getPosTan(mDistance + mPathLength / 2, mPos, mTan);
            } else {

                mPathMeasure.getPosTan(mDistance - mPathLength / 2, mPos, mTan);
            }
            mMatrix.postTranslate(mPos[0] - mBm_offsetX2, mPos[1] - mBm_offsetY2);
            canvas.drawBitmap(mBitmapMoon, mMatrix, null);
        } else {

            mDistance = 0;
        }


    }


    public void animateSecondScreenClock(float position) {

        if (mCurrentDirection == Path.Direction.CCW) {
            mCurrentDirection = Path.Direction.CW;
            //animPath.reset();
            initNewPath(Path.Direction.CW);
            invalidate();
        }

        if (Math.abs(position) > 1) {
            mDistance = mPathLength / 2 * (Math.abs(position));
        } else {

            mDistance = mPathLength / 2 * (Math.abs(position));
        }
        invalidate();
    }

    public void animateSecondScreenAntiClock(float position) {

        if (mCurrentDirection == Path.Direction.CW) {
            mCurrentDirection = Path.Direction.CCW;
            initNewPath(Path.Direction.CCW);
            invalidate();
        }

        if (Math.abs(position) > 1) {

        } else {

            mDistance = mPathLength / 2 * (Math.abs(1 + position));
        }
        invalidate();


    }


}
