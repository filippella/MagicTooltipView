package org.dalol.magictooltipview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public final class TooltipPopup {

    private final static int WHAT_DISMISS_AFTER = 0x4444;

    private final Context mContext;
    private final LayoutInflater mInflater;
    private long mDismissAfter;
    private View mTooltipView;
    private PopupWindow popupWindow;

    public TooltipPopup(Context context) {
        mContext = context;
        popupWindow = new PopupWindow(mContext);
        popupWindow.setTouchable(true);
        //        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(android.R.anim.fade_in);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                handler.removeMessages(WHAT_DISMISS_AFTER);
            }
        });
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public TooltipPopup setLayout(@LayoutRes int layout) {
        setView(mInflater.inflate(layout, new LinearLayout(mContext), false));
        return TooltipPopup.this;
    }

    public TooltipPopup setView(View view) {
        mTooltipView = view;
        popupWindow.setContentView(mTooltipView);
        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return TooltipPopup.this;
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        return mTooltipView.findViewById(viewId);
    }

    public TooltipPopup setAutoDismissTimeout(long duration) {
        mDismissAfter = duration;
        return TooltipPopup.this;
    }

    public TooltipPopup setFocusable(boolean focusable) {
        popupWindow.setFocusable(focusable);
        return TooltipPopup.this;
    }

    public void show(View anchorView) {
        show(anchorView, 0);
    }

    public void show(View anchorView, float offset) {
        mTooltipView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int measuredWidth = mTooltipView.getMeasuredWidth();

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);

        int halfTW = measuredWidth/2;
        int halfAV = anchorView.getMeasuredWidth()/2;

        int extraOffset = Utils.toPx(mContext.getResources().getDisplayMetrics(), offset);
        int xLocation = location[0] + (halfAV - halfTW);
        int yLocation = location[1] + anchorView.getMeasuredHeight() + extraOffset;

        //mTooltipView.setAlpha(0f);
//        mTooltipView.setScaleX(0.5f);
//        mTooltipView.setScaleY(0.5f);
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, xLocation, yLocation);
        //mTooltipView
        mTooltipView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
//        mTooltipView.animate()
//                .setInterpolator(new AccelerateDecelerateInterpolator())
//                .scaleX(1f)
//                .scaleY(1f)
//                .setDuration(100);
        if (mDismissAfter > 0) {
            handler.sendEmptyMessageDelayed(WHAT_DISMISS_AFTER, mDismissAfter);
        }
    }

    public boolean isTooltipShown() {
        return popupWindow != null && popupWindow.isShowing();
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mTooltipView.startAnimation(animation);
//            mTooltipView.animate()
//                    .setInterpolator(new AccelerateDecelerateInterpolator())
//                    .scaleX(0f)
//                    .scaleY(0f)
//                    .setDuration(150)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            if (popupWindow != null) {
//                                popupWindow.dismiss();
//                            }
//                        }
//                    });
        }
    };

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}

