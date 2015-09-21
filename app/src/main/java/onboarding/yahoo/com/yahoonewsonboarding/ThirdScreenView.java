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
import android.view.View;
import android.widget.Toast;

/**
 * Created by rahul.raja on 9/21/15.
 */
public class ThirdScreenView extends View {

    Paint paint;

    Bitmap bm;
    Bitmap mBitmap2,mBitmap3,mBitmap4,mBitmap5,mBitmap6;
    int bm_offsetX, bm_offsetY;

    Path animPath;
    PathMeasure pathMeasure;
    float pathLength;

    float step;   //distance each step
    float distance;  //distance moved

    float[] pos;
    float[] tan;

    Matrix matrix;

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
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        bm_offsetX = bm.getWidth()/2;
        bm_offsetY = bm.getHeight()/2;

        animPath = new Path();
        int radius=200;
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
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(animPath, paint);

        drawCircle(canvas,0+step);
        drawCircle(canvas,pathLength/6+step);
        drawCircle(canvas,pathLength/3+step);
        drawCircle(canvas,pathLength/2+step);
        drawCircle(canvas,(2*pathLength)/3+step);
        drawCircle(canvas, (5 * pathLength) / 6+step);

        invalidate();


    }

    private void drawCircle(Canvas canvas,float distance){

        if(distance < pathLength){
            pathMeasure.getPosTan(distance, pos, tan);

            matrix.reset();
            float degrees = (float)(Math.atan2(tan[1], tan[0])*180.0/Math.PI);
            matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
            matrix.postTranslate(pos[0]-bm_offsetX, pos[1]-bm_offsetY);

            canvas.drawBitmap(bm, matrix, null);


            step += 0.25;
        }else{
            distance = 0;
            step=0;
        }

    }

}
