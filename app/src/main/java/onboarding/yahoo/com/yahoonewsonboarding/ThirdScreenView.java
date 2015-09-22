package onboarding.yahoo.com.yahoonewsonboarding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by rahul.raja on 9/21/15.
 */
public class ThirdScreenView extends View {

    Paint paint;

    Bitmap bm;
    Bitmap mBitmap2,mBitmap3,mBitmap4,mBitmap5,mBitmap6,mBitmap7;
    int bm_offsetX, bm_offsetY;

    Path animPath;
    PathMeasure pathMeasure;
    float pathLength;

    float step;   //distance each step
    float distance;  //distance moved

    float[] pos;
    float[] tan;

    Matrix matrix;

    private boolean mShouldSpheresRotate=true;
    private float mPosition;
    private int mPageWidth;

    private FloatWrapper mDarkYellowOrigPos,mDarkYellowStep;
    private FloatWrapper mDarkGreenOrigPos,mDarkGreenStep;
    private FloatWrapper mYellowOrigPos,mYellowStep;
    private FloatWrapper mBlueOrigPos,mBlueStep;
    private FloatWrapper mLightGreenOrigPos,mLightGreenStep;
    private FloatWrapper mPinkOrigPos,mPinkStep;


    private boolean mAllCirclesDrawn=false;
    private float[] pos1=new float[2];
    private float[] pos2=new float[2];
    private float[] pos3=new float[2];
    private float[] pos4=new float[2];
    private float[] pos5=new float[2];
    private float[] pos6=new float[2];


    public ThirdScreenView(Context context) {
        super(context);
        initMyView();
    }

    public ThirdScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public ThirdScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView();
    }

    public void initMyView(){
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
//        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);





        bm_offsetX = mBitmap4.getWidth()/2;
        bm_offsetY = mBitmap4.getHeight()/2;

        animPath = new Path();
        int radius=180;
        int x=360,y=360;
        RectF rectF=new RectF(x-radius,y-radius,x+radius,y+radius);
        animPath.addArc(rectF, -90, 359);
        //animPath.addCircle(360, 320, 280, Path.Direction.CW);
        animPath.close();

        pathMeasure = new PathMeasure(animPath, false);
        pathLength = pathMeasure.getLength();

        Toast.makeText(getContext(), "pathLength: " + pathLength, Toast.LENGTH_LONG).show();

        step = 1;
        distance = 0;
        pos = new float[2];
        tan = new float[2];

        matrix = new Matrix();

        initOriginalPos();
    }


    private void initOriginalPos(){

        mDarkYellowOrigPos=new FloatWrapper(0f);
        mDarkGreenOrigPos=new FloatWrapper(pathLength/6);
        mYellowOrigPos=new FloatWrapper(pathLength/3);
        mBlueOrigPos=new FloatWrapper(pathLength/2);
        mLightGreenOrigPos=new FloatWrapper((2*pathLength)/3);
        mPinkOrigPos=new FloatWrapper((5*pathLength)/6);

        mDarkYellowStep=new FloatWrapper(0f);
        mDarkGreenStep=new FloatWrapper(0f);
        mYellowStep=new FloatWrapper(0f);
        mBlueStep=new FloatWrapper(0f);
        mLightGreenStep=new FloatWrapper(0f);
        mPinkStep=new FloatWrapper(0f);


    }




    @Override
    protected void onDraw(Canvas canvas) {

        //Log.d("PERMISSION",""+mShouldSpheresRotate);

        canvas.drawPath(animPath, paint);



//        float pos1[]=drawCircle(canvas, 0 + step,bm);
//        float pos2[]=drawCircle(canvas, pathLength / 6 + step,mBitmap2);
//        float pos3[]=drawCircle(canvas, pathLength / 3 + step,mBitmap3);
//        float pos4[]=drawCircle(canvas, pathLength / 2 + step,mBitmap4);
//        float pos5[]=drawCircle(canvas, (2 * pathLength) / 3 + step,mBitmap5);
//        float pos6[]=drawCircle(canvas, (5 * pathLength) / 6 + step,mBitmap6);



        if(mShouldSpheresRotate || !mAllCirclesDrawn){

            pos1 = drawCircle(canvas, mDarkYellowOrigPos, mDarkYellowStep, bm);
            pos2 = drawCircle(canvas, mDarkGreenOrigPos, mDarkGreenStep, mBitmap2);
            pos3 = drawCircle(canvas, mYellowOrigPos, mYellowStep, mBitmap3);
            pos4= drawCircle(canvas, mBlueOrigPos, mBlueStep, mBitmap4);
            pos5 = drawCircle(canvas, mLightGreenOrigPos, mLightGreenStep, mBitmap5);
            pos6 = drawCircle(canvas, mPinkOrigPos, mPinkStep, mBitmap6);
        }

        if(!mShouldSpheresRotate){

            float sphere1 = (float) (mPosition * 100);
            Log.d("POSITION1", "" + sphere1);
            canvas.drawBitmap(bm,pos1[0]+sphere1-bm_offsetX,pos1[1]-bm_offsetY,null);

            float sphere2 = (float) (mPosition * 400);
            Log.d("POSITION2", "" + sphere2);
            canvas.drawBitmap(mBitmap2,pos2[0]+sphere2-bm_offsetX,pos2[1]-bm_offsetY,null);

            float sphere3 = (float) (mPosition * 100);
            Log.d("POSITION3", "" + sphere3);
            canvas.drawBitmap(mBitmap3,pos3[0]+sphere3-bm_offsetX,pos3[1]-bm_offsetY,null);

            float sphere4 = (float) (mPosition * 800);
            Log.d("POSITION", "" + sphere4);
            canvas.drawBitmap(mBitmap4,pos4[0]+sphere4-bm_offsetX,pos4[1]-bm_offsetY,null);

            float sphere5 = (float) (mPosition * 200);
            Log.d("POSITION5", "" + sphere5);
            canvas.drawBitmap(mBitmap5,pos5[0]+sphere5-bm_offsetX,pos5[1]-bm_offsetY,null);

            float sphere6 = (float) (mPosition * 1100);
            Log.d("POSITION6", "" + sphere6);
            canvas.drawBitmap(mBitmap6,pos6[0]+sphere6-bm_offsetX,pos6[1]-bm_offsetY,null);
        }

        if(mShouldSpheresRotate) {
                invalidate();
        }

        mAllCirclesDrawn=true;

    }


//    private float[] drawCircle(Canvas canvas,float distance,Bitmap bm){
//
//        float positionArray[]=new float[2];
//        if(distance < pathLength){
//            pathMeasure.getPosTan(distance, pos, tan);
//
//            matrix.reset();
//            float degrees = (float)(Math.atan2(tan[1], tan[0])*180.0/Math.PI);
//            matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
//            matrix.postTranslate(pos[0]-bm_offsetX, pos[1]-bm_offsetY);
//
//            canvas.drawBitmap(bm, matrix, null);
//
//            if(mShouldSpheresRotate) {
//                step += 0.25;
//            }
//        }else{
//            distance = 0;
//            step=0;
//        }
//
//        positionArray[0]=pos[0];
//        positionArray[1]=pos[1];
//
//        return positionArray;
//
//    }


    private float[] drawCircle(Canvas canvas,FloatWrapper originalPos,FloatWrapper step,Bitmap bm){

        float distance=originalPos.floatValue+step.floatValue;
        float positionArray[]=new float[2];
        if(distance < pathLength){
            pathMeasure.getPosTan(distance, pos, tan);

            matrix.reset();
            float degrees = (float)(Math.atan2(tan[1], tan[0])*180.0/Math.PI);
            matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
            matrix.postTranslate(pos[0]-bm_offsetX, pos[1]-bm_offsetY);

            canvas.drawBitmap(bm, matrix, null);

            if(mShouldSpheresRotate) {
                step.floatValue += 1.2f;
            }
        }else{
            originalPos.floatValue = 0f;
            step.floatValue=0f;
        }

        positionArray[0]=pos[0];
        positionArray[1]=pos[1];

        return positionArray;

    }

    public void setRotatingPermission(boolean permission){

        mShouldSpheresRotate=permission;
        if(mShouldSpheresRotate){
            invalidate();
        }
    }

    //int i=0;
    public void translateTheSpheres(float position,int pageWidth){

        mPosition=position;
        mPageWidth=pageWidth;
        invalidate();



    }

    static class FloatWrapper{

        FloatWrapper(Float floatVal){

            this.floatValue=floatVal;
        }
        public Float floatValue;

    }

}
