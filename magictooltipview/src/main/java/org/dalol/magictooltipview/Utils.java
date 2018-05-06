package org.dalol.magictooltipview;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public final class Utils {

    private Utils() {}

    public static int toPx(DisplayMetrics metrics, float value) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics));
    }
}
