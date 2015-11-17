package com.joebruzek.oter.utilities;

import android.content.Context;

/**
 * Measurements is a helper class for any kind of value or dimension values
 *
 * Created by jbruzek on 11/17/15.
 */
public class Measurements {

    /**
     * Return the pixel number attached to a dp value
     * @param c Context
     * @param dp Value in dp
     * @return Value in pixels
     */
    public static int dpToPixel(Context c, int dp) {
        final float scale = c.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
