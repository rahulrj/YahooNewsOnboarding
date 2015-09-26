package onboarding.yahoo.com.yahoonewsonboarding;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by rahul.raja on 9/21/15.
 */
public class ThirdScreenView extends View {

    Paint paint;

    private float mRadius=180f;
    private float mCircleX=360f;
    private float mCircleY=360f;

    Bitmap bm;
    Bitmap mBitmap2, mBitmap3, mBitmap4, mBitmap5, mBitmap6, mBitmap7;
    int bm_offsetX, bm_offsetY;

    Path animPath;
    PathMeasure pathMeasure;
    float pathLength;

    float step;   //distance each step
    float distance;  //distance moved

    float[] pos;
    float[] tan;

    Matrix matrix;

    private boolean mShouldSpheresRotate = true;
    private float mPosition;
    private int mPageWidth;

    private FloatWrapper mDarkYellowOrigPos, mDarkYellowStep;
    private FloatWrapper mDarkGreenOrigPos, mDarkGreenStep;
    private FloatWrapper mYellowOrigPos, mYellowStep;
    private FloatWrapper mBlueOrigPos, mBlueStep;
    private FloatWrapper mLightGreenOrigPos, mLightGreenStep;
    private FloatWrapper mPinkOrigPos, mPinkStep;


    private boolean mAllCirclesDrawn = false;
    private boolean mStartNextScreen=false;
    private float[] pos1 = new float[2];
    private float[] pos2 = new float[2];
    private float[] pos3 = new float[2];
    private float[] pos4 = new float[2];
    private float[] pos5 = new float[2];
    private float[] pos6 = new float[2];



    /// Fourth animation
    private int UP_INTERVAL=50;
    private int UP_REFRESH_INTERVAL=10;
    private int UP_DISTANCE=100;
    private int UP_COUNTER=0;

    private float mNewPosX1;
    private float mNewPosY1;
    private float mX1Radians;


    /// Temps
    private float mTempX;
    private Path mTempPath;
    private float tempx,tempy;

    private float tempDist,tempPathLength;
    private PathMeasure tempPathMeasure;
    private float[] tempPos=new float[2];
    private float[] tempTan=new float[2];
    private float tempStep;
    private Matrix tempMatrix;
    private  boolean tempStartedNextScreen=false;



    private Path mPathLineDarkYellow,mPathLineDarkGreen,mPathLineYellow,mPathLineBlue,mPathLineLightGreen,mPathLinePink;
    private PathMeasure mPmDarkYellow,mPmDarkGreen,mPmYellow,mPmBlue,mPmLightGreen,mPmPink;
    private Matrix mLmDarkYellow,mLmDarkGreen,mLmYellow,mLmBlue,mLmLightGreen,mLmPink;
    private FloatWrapper mPlDarkYellow,mPlDarkGreen,mPlYellow,mPlBlue,mPlLightGreen,mPlPink;
    private FloatWrapper mDistDarkYellow,mDistDarkGreen,mDistYellow,mDistBlue,mDistLightGreen,mDistPink;
    private FloatWrapper mBscDarkYellow,mBscDarkGreen,mBscYellow,mBscBlue,mBscLightGreen,mBscPink;


    private float mTempDistanceX=20;
    private float mTempDistanceY=20;
    private boolean mUpperBoundHit;
    private int mBitmapScaleCounter=0;

    private Context mContext;


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

    public void initMyView() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        bm = Utils.getCircularBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.multipurpose_square),R.color.dark_yellow);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);
        mBitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.fb_log);


        bm_offsetX = mBitmap4.getWidth() / 2;
        bm_offsetY = mBitmap4.getHeight() / 2;

        animPath = new Path();
        //int radius = 180;
        //int x = 360, y = 360;
        RectF rectF = new RectF(mCircleX - mRadius, mCircleY - mRadius, mCircleX + mRadius, mCircleY + mRadius);
        animPath.addArc(rectF, -90, 359);
        //animPath.addCircle(360, 320, 280, Path.Direction.CW);
        animPath.close();

        pathMeasure = new PathMeasure(animPath, false);
        pathLength = pathMeasure.getLength();

        //Toast.makeText(getContext(), "pathLength: " + pathLength, Toast.LENGTH_LONG).show();

        step = 1;
        distance = 0;
        pos = new float[2];
        tan = new float[2];

        matrix = new Matrix();

        initOriginalPos();

        initLinePaths();
        mTempPath=new Path();
        mTempPath.moveTo(360,360);
    }

    private void initLinePaths(){

        mPathLineDarkYellow=new Path();
        mPathLineDarkGreen=new Path();
        mPathLineYellow=new Path();
        mPathLineBlue=new Path();
        mPathLineLightGreen=new Path();
        mPathLinePink=new Path();



        mPathLineDarkYellow.moveTo(mCircleX,mCircleY);
        mPathLineDarkGreen.moveTo(mCircleX,mCircleY);
        mPathLineYellow.moveTo(mCircleX,mCircleY);
        mPathLineBlue.moveTo(mCircleX,mCircleY);
        mPathLineLightGreen.moveTo(mCircleX,mCircleY);
        mPathLinePink.moveTo(mCircleX,mCircleY);

        mPlDarkYellow=new FloatWrapper(0f);
        mPlDarkGreen=new FloatWrapper(0f);
        mPlYellow=new FloatWrapper(0f);
        mPlBlue=new FloatWrapper(0f);
        mPlLightGreen=new FloatWrapper(0f);
        mPlPink=new FloatWrapper(0f);

        mDistDarkYellow=new FloatWrapper(mRadius);
        mDistDarkGreen=new FloatWrapper(mRadius);
        mDistYellow=new FloatWrapper(mRadius);
        mDistBlue=new FloatWrapper(mRadius);
        mDistLightGreen=new FloatWrapper(mRadius);
        mDistPink=new FloatWrapper(mRadius);

        mPmDarkYellow=new PathMeasure();
        mPmDarkGreen=new PathMeasure();
        mPmYellow=new PathMeasure();
        mPmBlue=new PathMeasure();
        mPmLightGreen=new PathMeasure();
        mPmPink=new PathMeasure();

        mLmDarkYellow=new Matrix();
        mLmDarkGreen=new Matrix();
        mLmYellow=new Matrix();
        mLmBlue=new Matrix();
        mLmLightGreen=new Matrix();
        mLmPink=new Matrix();

        mBscDarkYellow=new FloatWrapper(0f);
        mBscDarkGreen=new FloatWrapper(0f);
        mBscYellow=new FloatWrapper(0f);
        mBscBlue=new FloatWrapper(0f);
        mBscLightGreen=new FloatWrapper(0f);
        mBscPink=new FloatWrapper(0f);

    }

    private void initOriginalPos() {

        mDarkYellowOrigPos = new FloatWrapper(0f);
        mDarkGreenOrigPos = new FloatWrapper(pathLength / 6);
        mYellowOrigPos = new FloatWrapper(pathLength / 3);
        mBlueOrigPos = new FloatWrapper(pathLength / 2);
        mLightGreenOrigPos = new FloatWrapper((2 * pathLength) / 3);
        mPinkOrigPos = new FloatWrapper((5 * pathLength) / 6);

        mDarkYellowStep = new FloatWrapper(0f);
        mDarkGreenStep = new FloatWrapper(0f);
        mYellowStep = new FloatWrapper(0f);
        mBlueStep = new FloatWrapper(0f);
        mLightGreenStep = new FloatWrapper(0f);
        mPinkStep = new FloatWrapper(0f);


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


        if (mShouldSpheresRotate || !mAllCirclesDrawn) {

            Log.d("CALLED","ye");
            pos1 = drawCircle(canvas, mDarkYellowOrigPos, mDarkYellowStep, bm);
            pos2 = drawCircle(canvas, mDarkGreenOrigPos, mDarkGreenStep, mBitmap2);
            pos3 = drawCircle(canvas, mYellowOrigPos, mYellowStep, mBitmap3);
            pos4 = drawCircle(canvas, mBlueOrigPos, mBlueStep, mBitmap4);
            pos5 = drawCircle(canvas, mLightGreenOrigPos, mLightGreenStep, mBitmap5);
            pos6 = drawCircle(canvas, mPinkOrigPos, mPinkStep, mBitmap6);
        }

        if(mStartNextScreen){

            if(UP_COUNTER==0){

                //initLinePathPos();


                initLinePathVars(mPathLineDarkYellow, pos1, mPmDarkYellow, mLmDarkYellow, mPlDarkYellow);
                initLinePathVars(mPathLineDarkGreen, pos2, mPmDarkGreen, mLmDarkGreen, mPlDarkGreen);
                initLinePathVars(mPathLineYellow,pos3,mPmYellow,mLmYellow,mPlYellow);
                initLinePathVars(mPathLineBlue,pos4,mPmBlue,mLmBlue,mPlBlue);
                initLinePathVars(mPathLineLightGreen,pos5,mPmLightGreen,mLmLightGreen,mPlLightGreen);
                initLinePathVars(mPathLinePink, pos6, mPmPink, mLmPink, mPlPink);

                //canvas.drawPath(mPathLineDarkYellow,paint);

                UP_COUNTER+=1;
                mStartNextScreen=true;


            }

            canvas.drawPath(mPathLineDarkYellow,paint);
            boolean isAnim1Completed=moveCircleInOut(mDistDarkYellow, mPlDarkYellow, mLmDarkYellow, mPmDarkYellow, canvas, bm,mBscDarkYellow);
            boolean isAnim2Completed= moveCircleInOut(mDistDarkGreen,mPlDarkGreen,mLmDarkGreen,mPmDarkGreen,canvas,mBitmap2,mBscDarkGreen);
            boolean isAnim3Completed=moveCircleInOut(mDistYellow,mPlYellow,mLmYellow,mPmYellow,canvas,mBitmap3,mBscYellow);
            boolean isAnim4Completed=moveCircleInOut(mDistBlue,mPlBlue,mLmBlue,mPmBlue,canvas,mBitmap4,mBscBlue);
            boolean isAnim5Completed=moveCircleInOut(mDistLightGreen,mPlLightGreen,mLmLightGreen,mPmLightGreen,canvas,mBitmap5,mBscLightGreen);
            boolean isAnim6Completed=moveCircleInOut(mDistPink,mPlPink,mLmPink,mPmPink,canvas,mBitmap6,mBscPink);


            if(isAnim1Completed && isAnim2Completed && isAnim3Completed && isAnim4Completed && isAnim5Completed && isAnim6Completed){

                startNewActivity();
                return;
            }

//            if(UP_COUNTER==0){
//
//                mTempPath.lineTo(pos1[0],pos1[1]);
//                float degress=pos1[2];
//
//                if(degress>0 && degress<90){
//
//                    tempx=pos1[0]+100;
//                    tempy=pos1[1]-100;
//                }
//
//                if(degress>90 && degress<180){
//
//                    tempx=pos1[0]+100;
//                    tempy=pos1[0]+100;
//                }
//
//                if(degress>-180 && degress<-90){
//
//                    tempx=pos1[0]-100;
//                    tempy=pos1[1]+100;
//                }
//
//                if(degress>-90 && degress<0){
//
//                    tempx=pos1[0]-100;
//                    tempy=pos1[1]-100;
//                }
//
//                mTempPath.lineTo(tempx,tempy);
//                mTempPath.close();
//                tempDist=180;
//                tempPathMeasure = new PathMeasure(mTempPath, false);
//                tempPathLength = tempPathMeasure.getLength();
//                tempMatrix=new Matrix();
//                UP_COUNTER+=1;
//                mStartNextScreen=true;
//            }
//
//            if (tempDist < tempPathLength) {
//                tempPathMeasure.getPosTan(tempDist, tempPos, tempTan);
//
//                tempMatrix.reset();
//                //degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
//                //matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
//                tempMatrix.postTranslate(tempPos[0] - bm_offsetX, tempPos[1] - bm_offsetY);
//
//                canvas.drawBitmap(bm, tempMatrix, null);
//
//               tempDist+=1;
//            } else {
//                //originalPos.floatValue = 0f;
//                tempDist-=1;
//            }

            invalidate();



//
//            if(UP_COUNTER==0){
//                mNewPosX1=pos1[0];
//                //mX1Radians=(float)Math.atan2(pos1[1],pos1[0]);
//                mX1Radians=(float)Math.toDegrees(pos1[2]);
//                Log.d("ANGLE",pos1[0]+" "+pos1[1]+" "+pos1[2]);
//
//
//                mTempX=pos1[0];
//            }
//
//            mTempX=(float)(mTempX-((UP_DISTANCE*1.0)/UP_REFRESH_INTERVAL));
//            mNewPosX1=(float)(mNewPosX1+((UP_DISTANCE*1.0)/UP_REFRESH_INTERVAL));
//
//            if(pos1[1]>=360 && pos1[0]>=360){
//
//                mNewPosY1 = (float) (mNewPosX1 * Math.tan(mX1Radians));
//                canvas.drawBitmap(bm,mNewPosX1-bm_offsetX,mNewPosY1-bm_offsetY,null);
//                Log.d("QUADS", "QUAD2");
//            }
//            else if(pos1[1]<360 && pos1[0]>=360) {
//                mNewPosY1 = (float) (mTempX / Math.tan(mX1Radians));
//                canvas.drawBitmap(bm,mNewPosX1-bm_offsetX,mNewPosY1-bm_offsetY,null);
//                Log.d("QUADS","QUAD1");
//            }
//            else if(pos1[1]>=360 && pos1[0]<360){
//
//
//                mNewPosY1 = (float) (mNewPosX1 * Math.tan(mX1Radians));
//                canvas.drawBitmap(bm,mTempX-bm_offsetX,mNewPosY1-bm_offsetY,null);
//                Log.d("QUADS", "QUAD3");
//            }
//            else if(pos1[1]<360 && pos1[0]<360){
//
//
//                mNewPosY1 = (float) (mTempX * Math.tan(mX1Radians));
//                canvas.drawBitmap(bm,mTempX-bm_offsetX,mNewPosY1-bm_offsetY,null);
//                Log.d("QUADS", "QUAD4");
//            }
//
//
//
//
//
//
//
//            Log.d("NEXT",""+mNewPosX1+" "+mNewPosY1+" "+(UP_DISTANCE/UP_REFRESH_INTERVAL)+" "+(Math.toDegrees(mX1Radians)));
//
//            UP_COUNTER+=1;
//            if(UP_COUNTER<= UP_DISTANCE/UP_REFRESH_INTERVAL) {
//                invalidate();
//            }
        }

        if (!mShouldSpheresRotate && !mStartNextScreen) {

            Log.d("CALLED","wo");
            float sphere1 = (float) (mPosition * 100);
            Log.d("POSITION1", "" + sphere1);
            canvas.drawBitmap(bm, pos1[0] + sphere1 - bm_offsetX, pos1[1] - bm_offsetY, null);


            float sphere2 = (float) (mPosition * 400);
            Log.d("POSITION2", "" + sphere2);
            canvas.drawBitmap(mBitmap2, pos2[0] + sphere2 - bm_offsetX, pos2[1] - bm_offsetY, null);

            float sphere3 = (float) (mPosition * 100);
            Log.d("POSITION3", "" + sphere3);
            canvas.drawBitmap(mBitmap3, pos3[0] + sphere3 - bm_offsetX, pos3[1] - bm_offsetY, null);

            float sphere4 = (float) (mPosition * 800);
            Log.d("POSITION4", "" + sphere4);
            canvas.drawBitmap(mBitmap4, pos4[0] + sphere4 - bm_offsetX, pos4[1] - bm_offsetY, null);

            float sphere5 = (float) (mPosition * 200);
            Log.d("POSITION5", "" + sphere5);
            canvas.drawBitmap(mBitmap5, pos5[0] + sphere5 - bm_offsetX, pos5[1] - bm_offsetY, null);

            float sphere6 = (float) (mPosition * 1100);
            Log.d("POSITION6", "" + sphere6);
            canvas.drawBitmap(mBitmap6, pos6[0] + sphere6 - bm_offsetX, pos6[1] - bm_offsetY, null);
        }

        if (mShouldSpheresRotate) {
            invalidate();
        }

        mAllCirclesDrawn = true;

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




    private void initLinePathPos(){

        mPathLineDarkYellow.moveTo(pos1[0],pos1[1]);
        mPathLineDarkGreen.moveTo(pos2[0],pos2[1]);
        mPathLineYellow.moveTo(pos3[0],pos3[1]);
        mPathLineBlue.moveTo(pos4[0],pos4[1]);
        mPathLineLightGreen.moveTo(pos5[0],pos5[1]);
        mPathLinePink.moveTo(pos6[0],pos6[1]);
    }

    private void initLinePathVars(Path path,float[] pos,PathMeasure pm,Matrix matrix,FloatWrapper pathLength){

        float x=0f,y=0f;

        if(pos[2]>0 && pos[2]<90){

            x=pos[0]+mTempDistanceX;
            y=pos[1]-mTempDistanceY;

        }

        if(pos[2]>90 && pos[2]<180){

            x=pos[0]+mTempDistanceX;
            y=pos[1]+mTempDistanceY;

        }

        if(pos[2]>-180 && pos[2]<-90){

            x=pos[0]-mTempDistanceX;
            y=pos[1]+mTempDistanceY;

        }

        if(pos[2]>-90 && pos[2]<0){

            x=pos[0]-mTempDistanceX;
            y=pos[1]-mTempDistanceY;

        }

        path.lineTo(pos[0], pos[1]);
        path.lineTo(x, y);
        path.close();
        //tempDist=180;
        //pm = new PathMeasure(path, false);
        pm.setPath(path, false);
        pathLength.floatValue = pm.getLength();
        //matrix=new Matrix();



    }

    private boolean moveCircleInOut(FloatWrapper distance,FloatWrapper pathLength,Matrix matrix,PathMeasure pathMeasure,Canvas canvas,Bitmap bm,FloatWrapper scaleCounter){


        float position[]=new float[2];
        float tangent[]=new float[2];


        if(distance.floatValue>=(pathLength.floatValue/2)){
            mUpperBoundHit=true;

        }

        if(mUpperBoundHit){

            int newWidth=(int)(bm.getWidth()-4*(scaleCounter.floatValue/1));
            int newHeight=(int)(bm.getHeight()-4*(scaleCounter.floatValue/1));
            if(newWidth>=15 && newHeight >=15) {


                //if(mBitmapScaleCounter%5==0){

                    bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
                    Log.d("SCALED","CALED"+newWidth+" "+newHeight);
                //}
                scaleCounter.floatValue+=1;

            }
            else{

                bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            }



        }


        if (distance.floatValue < pathLength.floatValue) {
            pathMeasure.getPosTan(distance.floatValue, position,tangent);
            matrix.reset();
                //degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
                //matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
            matrix.postTranslate(position[0] - bm.getWidth() / 2, position[1] - bm.getHeight() / 2);
            canvas.drawBitmap(bm, matrix, null);

            //Log.d("SCALED",""+bm.getWidth()+" "+bm.getHeight());


               if(mUpperBoundHit){

                   distance.floatValue+=10;
                   Log.d("HIT","TRUE"+" "+distance);
               }
            else {
                   distance.floatValue += 1;
               }
        } else {

            return true;
            //startNewActivity();
                //originalPos.floatValue = 0f;
                //distance.floatValue-=1;
            //mUpperBoundHit=true;
        }

        return false;
    }

    private float[] drawCircle(Canvas canvas, FloatWrapper originalPos, FloatWrapper step, Bitmap bm) {

        float distance = originalPos.floatValue + step.floatValue;
        float positionArray[] = new float[3];
        float degrees=0f;
        if (distance < pathLength) {
            pathMeasure.getPosTan(distance, pos, tan);

            matrix.reset();
            degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
            matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
            matrix.postTranslate(pos[0] - bm_offsetX, pos[1] - bm_offsetY);

            canvas.drawBitmap(bm, matrix, null);

            if (mShouldSpheresRotate) {
                step.floatValue += 1.2f;
            }
        } else {
            originalPos.floatValue = 0f;
            step.floatValue = 0f;
            //invalidate();
        }

        positionArray[0] = pos[0];
        positionArray[1] = pos[1];
        positionArray[2]=degrees;

        return positionArray;

    }

    public void setRotatingPermission(boolean permission) {

        mShouldSpheresRotate = permission;
        if (mShouldSpheresRotate) {
            invalidate();
        }
    }

    //int i=0;
    public void translateTheSpheres(float position, int pageWidth) {

        mPosition = position;
        mPageWidth = pageWidth;
        invalidate();


    }

    public void startNextScreen(){

        mStartNextScreen=true;
        mShouldSpheresRotate=false;
        invalidate();

    }

    public void setContext(Context context){

        mContext=context;
    }

    private void startNewActivity(){

        Intent i=new Intent(mContext,NewsActivity.class);
        mContext.startActivity(i);
        Log.d("STARTED","true");
    }


}
