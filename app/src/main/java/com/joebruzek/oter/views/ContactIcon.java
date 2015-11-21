package com.joebruzek.oter.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.joebruzek.oter.R;
import com.joebruzek.oter.models.Contact;
import com.joebruzek.oter.utilities.Measurements;
import com.joebruzek.oter.utilities.Strings;

/**
 * ContactIcon is a custom view that is a circle icon with the initials of a person in white text
 *
 * Created by jbruzek on 11/16/15.
 */
public class ContactIcon extends View {

    private Paint paintStyle;

    private final int preferredSize = 40;

    private String contactName;
    private String initials;
    private int iconColor;
    private Contact contact;

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


        //Set the picture if there is one
        if (contact != null && contact.getPicture() != null) {
            Rect result = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
            Bitmap newBitmap = getRoundedCornerBitmap(contact.getPicture(), canvas.getHeight() / 2);
            canvas.drawBitmap(newBitmap, null, result, paintStyle);
        }
        //draw the text if there is any
        else if (!initials.equals("")) {
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

        int pixelSize = Measurements.dpToPixel(getContext(), preferredSize);

        //Get the width measurement
        int widthSize = View.resolveSize(pixelSize, widthMeasureSpec);

        //Get the height measurement
        int heightSize = View.resolveSize(pixelSize, heightMeasureSpec);

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

    /**
     * Set the contact model for this icon
     * @param c
     */
    public void setContact(Contact c) {
        this.contact = c;
        this.contactName = c.getName();
        this.initials = Strings.getInitials(c.getName());
        invalidate();
        requestLayout();
    }

    /**
     * Turn a bitmap into a rounded bitmap. Code taken from here:
     * http://stackoverflow.com/a/3292810/3682482
     * @param bitmap
     * @param pixels
     * @return
     */
    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
