package org.dalol.magictooltipview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MagicTooltipView extends FrameLayout {

    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private RectF mBounds;
    private int mArrowHeight;
    private int mBoundsCorner;
    private int mCenter;
    private int mStartColor, mEndColor;
    private boolean mIsShading;

    public MagicTooltipView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mContext = context;
        setWillNotDraw(false);
        mArrowHeight = Utils.toPx(displayMetrics, 10);
        mBoundsCorner = Utils.toPx(displayMetrics, 5);

        int padding = Utils.toPx(displayMetrics, 8);
        int sidePadding = Utils.toPx(displayMetrics, 20);
        setPadding(sidePadding, padding + mArrowHeight, sidePadding, padding);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        mBounds = new RectF();
    }

    public void setTooltipBgColors(int startColor, int endColor) {
        mStartColor = ContextCompat.getColor(mContext, startColor);
        mEndColor = ContextCompat.getColor(mContext, endColor);
        mIsShading = true;
    }

    public void setTooltipBgColor(int color) {
        mPaint.setShader(null);
        mPaint.setColor(color);
        mIsShading = false;
    }

    public void setText(String message, float fontSize, boolean allCaps) {
        TextView tv = new TextView(mContext);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(fontSize);
        tv.setText(message);
        tv.setAllCaps(allCaps);
        addViewToParent(tv);
    }

    public void setView(@LayoutRes int layoutRes) {
        addViewToParent(inflate(getContext(), layoutRes, null));
    }

    private void addViewToParent(View view) {
        removeAllViews();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(view, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + mArrowHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBounds = new RectF(0, mArrowHeight, w, h);
        mCenter = w / 2;
        if(mIsShading) mPaint.setShader(new LinearGradient(0, 0, 0, h, mStartColor, mEndColor, Shader.TileMode.MIRROR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mPath.addRoundRect(mBounds, mBoundsCorner, mBoundsCorner, Path.Direction.CW);
        mPath.moveTo(mCenter, 0f);
        mPath.lineTo(mCenter - mArrowHeight, mBounds.top);
        mPath.lineTo(mCenter + mArrowHeight, mBounds.top);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }
}
