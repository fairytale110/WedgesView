/**
 * @class tech.nicesky.wedgesview.WedgesView
 * @date on 2018/9/18-1:12
 * @author fairytale110
 * @email  fairytale110@foxmail.com
 * @description:
 *
 */
package tech.nicesky.libwedgesview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @class tech.nicesky.wedgesview.WedgesView
 * @date on 2018/9/18-1:12
 * @author fairytale110
 * @email  fairytale110@foxmail.com
 * @description:
 *
 */
@SuppressWarnings("ALL")
public class WedgesView extends View {
    private String TAG = getClass().getSimpleName();
    private Paint paint;
    private RectF rectF;
    private Context context;
    private ValueAnimator animator;
    private int diameterDefault = 200;
    private int wedgeDiameter;
    private int paintAlpha = (int) (255F * 0.8F);
    private int colorA, colorB, colorC, colorD;
    private int mPaddingStart, mPaddingEnd, mPaddingTop, mPaddingBottom;
    private float rotateSpeed = 0.5F;
    private float sweepAngle = 90.0F;
    private float startAngleA = 90F, startAngleB = 0F, startAngleC = -90F, startAngleD = -180F;
    private float angle_max = 360F*100000F;

    public WedgesView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public WedgesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public WedgesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WedgesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    /**
     * Initialize some necessary properties
     *
     * @param attrs see {@link AttributeSet}
     */
    private void init(@Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(paintAlpha);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        rectF = new RectF(0, 0, wedgeDiameter, wedgeDiameter);
        diameterDefault = (int) context.getResources().getDimension(R.dimen.dp_200);

        setBackgroundColor(Color.parseColor("#FFFFFF"));
        initStartAngle();

        initAttr(context, attrs);
        anim();
    }

    /**
     * @param context see {@link Context}
     * @param attrs   see  {@link AttributeSet}
     */
    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.WedgesView);

            int backgroundColor     = attributes.getColor(R.styleable.WedgesView_wv_background, Color.parseColor("#FFFFFF"));
            wedgeDiameter           = (int) attributes.getDimension(R.styleable.WedgesView_wv_wedge_diameter, context.getResources().getDimension(R.dimen.dp_200));
            rotateSpeed             = attributes.getFloat(R.styleable.WedgesView_wv_rotate_speed, 0.5F);
            float wedgeAlpha        = attributes.getFloat(R.styleable.WedgesView_wv_wedge_alpha, 0.8F);

            setBackgroundColor(backgroundColor);
            setWedgeAlpha(wedgeAlpha);
            setRotateSpeed(rotateSpeed);

            colorA = attributes.getColor(R.styleable.WedgesView_wv_wedge_color_first, Color.parseColor("#C2DFD7"));
            colorB = attributes.getColor(R.styleable.WedgesView_wv_wedge_color_second, Color.parseColor("#FFE6F5"));
            colorC = attributes.getColor(R.styleable.WedgesView_wv_wedge_color_third, Color.parseColor("#FE718D"));
            colorD = attributes.getColor(R.styleable.WedgesView_wv_wedge_color_fourth, Color.parseColor("#E90C59"));

            attributes.recycle();
        }
    }

    /**
     * Set wedges's color
     * @param colors  int array, and its length must be 4
     */
    public void setColors(int...colors) {
        if (colors == null){
            throw  new IllegalArgumentException("colors cant not be null");
        }
        if (colors.length < 4){
            throw  new IllegalArgumentException("colors's length must be 4");
        }
        this.colorA = colors[0];
        this.colorB = colors[1];
        this.colorC = colors[2];
        this.colorD = colors[3];
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    /**
     * Initialize Start Angle of the four wedges
     */
    private void initStartAngle() {
        startAngleA = 90F;
        startAngleB = 0F;
        startAngleC = -90F;
        startAngleD = -180F;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width  = getMySize(diameterDefault, widthMeasureSpec);
        int height = getMySize(diameterDefault, heightMeasureSpec);

        if (width  < height) {
            height = width;
        } else {
            width  = height;
        }
        if (wedgeDiameter > width) {
            wedgeDiameter = width;
        }
        if (diameterDefault > width) {
            wedgeDiameter   = width;
        }
        rectF.set(0, 0, wedgeDiameter, wedgeDiameter);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPaddingStart   = getPaddingStart();
        mPaddingEnd     = getPaddingEnd();
        mPaddingTop     = getPaddingTop();
        mPaddingBottom  = getPaddingBottom();

        rectF.set(mPaddingStart, mPaddingTop, wedgeDiameter - mPaddingEnd, wedgeDiameter - mPaddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //First draw the innermost wedge, from inside to outside
        paint.setColor(colorD);
        paint.setAlpha(paintAlpha);
        canvas.drawArc(rectF, startAngleD, sweepAngle, true, paint);

        paint.setColor(colorC);
        paint.setAlpha(paintAlpha);
        canvas.drawArc(rectF, startAngleC, sweepAngle, true, paint);

        paint.setColor(colorB);
        paint.setAlpha(paintAlpha);
        canvas.drawArc(rectF, startAngleB, sweepAngle, true, paint);

        paint.setColor(colorA);
        paint.setAlpha(paintAlpha);
        canvas.drawArc(rectF, startAngleA, sweepAngle, true, paint);

    }

    /**
     * Initialization Animator
     */
    private void anim() {
        animator = ValueAnimator.ofInt(0);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(animation -> {

            //Prevent infinity
            if (startAngleD > angle_max || startAngleA > angle_max){
                initStartAngle();
            }
            startAngleD = startAngleD + rotateSpeed * 10F;
            startAngleC = startAngleC + rotateSpeed * 8F;
            startAngleB = startAngleB + rotateSpeed * 6F;
            startAngleA = startAngleA + rotateSpeed * 4F;
            postInvalidate();
        });
        ValueAnimatorUtil.resetDurationScale();
        reStart();
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    /**
     * Set rotate speed for wedges.
     *
     * @param rotateSpeed float value, must between 0.1F and 1.0F.
     */
    public void setRotateSpeed(float rotateSpeed) {
        if (rotateSpeed <= 0.1F) {
            rotateSpeed = 0.1F;
        } else if (rotateSpeed >= 1F) {
            rotateSpeed = 0.99F;
        }
        this.rotateSpeed = rotateSpeed;
        reStart();
    }

    /**
     * Get rotate speed of wedges.
     *
     * @return rotate speed of wedges.
     */
    public float getRotateSpeed() {
        return rotateSpeed;
    }

    public void setWedgeAlpha(float wedgeAlpha) {
        if (wedgeAlpha < 0.1F ){
            wedgeAlpha = 0.1F;
        }

        if (wedgeAlpha > 1.0F ){
            wedgeAlpha = 1.0F;
        }
        this.paintAlpha = (int) (255F * wedgeAlpha);
    }

    public void setWedgeDiameter(int wedgeDiameter) {
        this.wedgeDiameter = wedgeDiameter;
    }

    /**
     * Reset and reStart the animation
     */
    public void reStart() {
        if (animator == null) return;
        stop();
        animator.start();
    }

    /**
     * Stop the animation
     */
    public void stop(){
        initStartAngle();
        if (animator == null) return;
        if (animator.isRunning()) {
            animator.cancel();
        }
    }
}
