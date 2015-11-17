package com.joebruzek.oter.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.joebruzek.oter.R;
import com.joebruzek.oter.utilities.Strings;

/**
 * ContactIcon is a custom view that is a circle icon with the initials of a person in white text
 *
 * Created by jbruzek on 11/16/15.
 */
public class ContactIcon extends View {

    private Paint paintStyle;

    private String contactName;
    private String initials;
    private int iconColor;

    /**
     * Constructor. Get the attributes from the XML declaration
     * @param context
     * @param attrs
     */
    public ContactIcon(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Default Values
        contactName = "";
        initials = "";
        iconColor = getResources().getColor(R.color.accent);;

        paintStyle = new Paint();
        TypedArray attributesArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ContactIcon, 0, 0);
        try {
            contactName = attributesArray.getString(R.styleable.ContactIcon_contactName);
            initials = Strings.getInitials(contactName);
            iconColor = attributesArray.getInteger(R.styleable.ContactIcon_iconColor, 0);
        } finally {
            attributesArray.recycle();
        }
    }

    /**
     * Handle drawing events for the icon. This is where the circle and the text are created
     * @param canvas the canvas onto which the icon is drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        paintStyle.setStyle(Paint.Style.FILL);
        paintStyle.setAntiAlias(true);
        paintStyle.setColor(iconColor);

        int centerX = this.getMeasuredWidth() / 2;
        int centerY = this.getMeasuredHeight() / 2;
        int radius = Math.min(this.getMeasuredHeight(), this.getMeasuredWidth()) / 2;
        canvas.drawCircle(centerX, centerY, radius, paintStyle);

        //draw the text if there is any
        if (!initials.equals("")) {
            paintStyle.setColor(getResources().getColor(R.color.white));
            paintStyle.setTextAlign(Paint.Align.CENTER);
            float textSize = radius;
            paintStyle.setTextSize(textSize);
            paintStyle.setAntiAlias(true);

            Rect bounds = new Rect();
            paintStyle.getTextBounds(initials, 0, 1, bounds);
            canvas.drawText(initials, centerX, (this.getMeasuredHeight() + bounds.height()) / 2, paintStyle);
        }
    }

    /**
     * provide measurements for the view. This is used to determine bounds
     *
     * @param widthMeasureSpec width as defined by xml
     * @param heightMeasureSpec height as defined by xml
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //TODO: find a better default size than a constant. Something density dependant, like 24dp

        //Get the width measurement
        int widthSize = View.resolveSize(150, widthMeasureSpec);

        //Get the height measurement
        int heightSize = View.resolveSize(150, heightMeasureSpec);

        //MUST call this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     * Set the iconColor attribute programmatically
     * @param color
     */
    public void setIconColor(int color) {
        this.iconColor = color;
        invalidate();
        requestLayout();
    }

    /**
     * set the contactName attribute programmatically
     * @param name
     */
    public void setContactName(String name) {
        this.contactName = name;
        initials = Strings.getInitials(name);
        invalidate();
        requestLayout();
    }
}
