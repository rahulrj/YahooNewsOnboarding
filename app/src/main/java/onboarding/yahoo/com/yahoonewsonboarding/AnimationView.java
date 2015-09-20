package onboarding.yahoo.com.yahoonewsonboarding;

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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by rahul.raja on 9/19/15.
 */
public class AnimationView extends View {

    Paint paint;

    Bitmap bm;
    int bm_offsetX, bm_offsetY;

    Path animPath;
    PathMeasure pathMeasure;
    float pathLength;

    float step;   //distance each step
    float distance;  //distance moved

    float[] pos;
    float[] tan;

    Matrix matrix;
    Path.Direction mCurrentDirection = Path.Direction.CW;

    public AnimationView(Context context) {
        super(context);
        initMyView();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView();
    }

    public void initMyView() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        DashPathEffect dashPath = new DashPathEffect(new float[]{7, 7}, (float) 1.0);

        paint.setPathEffect(dashPath);

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bm_offsetX = bm.getWidth() / 2;
        bm_offsetY = bm.getHeight() / 2;


        initNewPath(Path.Direction.CW);

        step = 1;
        distance = 0;
        pos = new float[2];
        tan = new float[2];

        matrix = new Matrix();
    }

    private void initNewPath(Path.Direction dir) {

        //animPath.reset();
        animPath = new Path();
        //RectF rectF=new RectF(80,40,640,600);
        //animPath.addArc(rectF,90,360);
        animPath.addCircle(360, 320, 280, dir);

        animPath.close();

        pathMeasure = new PathMeasure(animPath, false);
        pathLength = pathMeasure.getLength();

        Toast.makeText(getContext(), "pathLength: " + pathLength, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(animPath, paint);

        if (distance < pathLength) {
            pathMeasure.getPosTan(distance, pos, tan);

            Log.d("RAHUl", "" + pos[0] + " " + pos[1] + " " + tan[0] + " " + tan[1]);

            matrix.reset();
            float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
            matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
            matrix.postTranslate(pos[0] - bm_offsetX, pos[1] - bm_offsetY);

            canvas.drawBitmap(bm, matrix, null);

            //distance += step;
        } else {
            distance = 0;
        }

        //invalidate();

    }

    public void moveTheView(float pos) {

        step = Math.abs(pos) * 5;
        Log.d("STEP", "" + step + " " + pos);
        invalidate();
    }

    public void animateSecondScreenClock(float position) {


        if (mCurrentDirection == Path.Direction.CCW) {
            mCurrentDirection = Path.Direction.CW;
            //animPath.reset();
            initNewPath(Path.Direction.CW);
            invalidate();
        }

        if (Math.abs(position) > 1) {
            distance = pathLength / 2 * (Math.abs(position));
        } else {

            distance = pathLength / 2 * (Math.abs(position));
        }
        invalidate();
    }

    public void animateSecondScreenAntiClock(float position) {

        if (mCurrentDirection == Path.Direction.CW) {
            mCurrentDirection = Path.Direction.CCW;
            //animPath.reset();
            initNewPath(Path.Direction.CCW);
            invalidate();
        }

        if (Math.abs(position) > 1) {
            //distance = pathLength/2 * (Math.abs(position));
        } else {


            //Log.d("RAHUL",""+distance);
            distance = pathLength / 2 * (Math.abs(1+position));
        }
        invalidate();


    }


}
