package fr.cvlaminck.hwweather.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import fr.cvlaminck.hwweather.R;

public class CircleSliderLayout
        extends ViewGroup {
    private final static String TAG = CircleSliderLayout.class.getSimpleName();

    /**
     * The offset is automatically calculated by the view
     * to center itself in the space provided by its parent as the
     * view is always square.
     */
    private int offsetTop;
    private int offsetLeft;

    /**
     * Should this view use antialiasing to draw itself.
     */
    private boolean useAntiAliasing;

    /**
     * CENTER, CENTER_VERTICALLY, CENTER_HORIZONTALLY, LEFT, RIGHT, TOP, BOTTOM
     */
    private int gravity;

    /**
     * Size in pixels of the border separating the progress from the children views.
     * May be set to 0;
     */
    private int borderWidth;

    /**
     * Color of the border separating the progress from the children views.
     */
    private int borderColor;

    /**
     * Clockwise angle where the zero value should be.
     * At 0, the zero value is at the top of the progress.
     * At 180, the zero value is at the bottom of the progress.
     */
    private float progressStartOffset;

    private float progressValue;
    private double progressAngle;

    private float progressMaxValue;

    /**
     * Color of the progress track
     */
    private int progressTint;

    /**
     * Width of the progress track
     */
    private int progressWidth;

    /**
     * Color of the background of the progress track
     */
    private int progressBackgroundColor;

    private float secondaryProgressValue;

    private float secondaryProgressMaxValue;

    /**
     * Color of the secondary progress track
     */
    private int secondaryProgressTint;

    /**
     * Width of the secondary track. May be larger than the width of
     * the primary.
     */
    private int secondaryProgressWidth;

    /**
     * Factor that will augment/reduce the size of the zone around
     * the progress where touch event will be caught.
     */
    private float touchZoneFactor;

    /**
     * Debug option that will display the touch zone.
     */
    private boolean touchZoneVisible = false;

    double touchZoneStartRadius;
    double touchZoneEndRadius;

    private boolean touchToSeekEnabled = true;

    private OnCircleSliderLayoutChangeListener changeListener = null;

    private Drawable thumb;
    private ColorStateList thumbTint = null;
    private PorterDuff.Mode thumbTintMode = null;

    private boolean dragging = false;

    private Rect tempRect = new Rect();
    private MotionEvent.PointerCoords tempCoords = new MotionEvent.PointerCoords();

    private Paint borderPaint = null;
    private Paint progressTrackPaint = null;
    private Paint secondaryProgressTrackPaint = null;
    private Paint progressTrackBackgroundPaint = null;
    private Paint touchZonePaint = null;

    private Bitmap controlDrawingCache = null;
    private boolean controlDrawingCacheDirty = true;
    private Paint controlDrawingCachePaint = null;

    public static final Property<CircleSliderLayout, Float> PROGRESS = Property.of(CircleSliderLayout.class, Float.class, "progressValue");
    public static final Property<CircleSliderLayout, Float> PROGRESS_MAX_VALUE = Property.of(CircleSliderLayout.class, Float.class, "progressMaxValue");
    public static final Property<CircleSliderLayout, Float> SECONDARY_PROGRESS = Property.of(CircleSliderLayout.class, Float.class, "secondaryProgressValue");
    public static final Property<CircleSliderLayout, Float> SECONDARY_PROGRESS_MAX_VALUE = Property.of(CircleSliderLayout.class, Float.class, "secondaryProgressMaxValue");
    public static final Property<CircleSliderLayout, Float> PROGRESS_START_OFFSET = Property.of(CircleSliderLayout.class, Float.class, "progressStartOffset");

    public CircleSliderLayout(Context context) {
        super(context);
        init(context);
        invalidatePaints();
    }

    public CircleSliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithAttrs(context, attrs);
        invalidatePaints();
    }

    public CircleSliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttrs(context, attrs);
        invalidatePaints();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleSliderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Provides default value for layout attributes.
     */
    private void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        useAntiAliasing = true;

        borderWidth = dipToPixels(0.5f, displayMetrics);
        borderColor = Color.TRANSPARENT;

        progressValue = 0f;
        progressMaxValue = 1f;
        progressWidth = dipToPixels(4f, displayMetrics);
        progressTint = Color.BLUE; //FIXME: Change this color to a material one
        progressBackgroundColor = Color.LTGRAY; //FIXME: Change this color to a material one

        secondaryProgressValue = 1f;
        secondaryProgressMaxValue = 1f;
        secondaryProgressWidth = dipToPixels(4f, displayMetrics);
        secondaryProgressTint = Color.DKGRAY; //FIXME: Change this color to a material one

        thumb = null;
        thumbTint = ColorStateList.valueOf(progressTint);
        thumbTintMode = PorterDuff.Mode.DST;

        touchToSeekEnabled = true;
    }

    private int dipToPixels(float dip, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
        return Math.round(pixels);
    }

    private void initWithAttrs(Context context, AttributeSet attrs) {
        init(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleSliderLayout,
                0, 0);

        useAntiAliasing = a.getBoolean(R.styleable.CircleSliderLayout_useAntiAliasing, useAntiAliasing);

        borderWidth = a.getDimensionPixelSize(R.styleable.CircleSliderLayout_borderWidth, borderWidth);
        borderColor = a.getColor(R.styleable.CircleSliderLayout_borderColor, borderColor);

        float progressValue = a.getFloat(R.styleable.CircleSliderLayout_progressValue, this.progressValue);
        float progressMaxValue = a.getFloat(R.styleable.CircleSliderLayout_progressMaxValue, this.progressMaxValue);
        progressWidth = a.getDimensionPixelSize(R.styleable.CircleSliderLayout_progressWidth, progressWidth);
        progressTint = a.getColor(R.styleable.CircleSliderLayout_progressTint, progressTint);
        progressBackgroundColor = a.getColor(R.styleable.CircleSliderLayout_progressBackgroundColor, progressBackgroundColor);

        float secondProgressValue = a.getFloat(R.styleable.CircleSliderLayout_secondaryProgressValue, this.secondaryProgressValue);
        float secondProgressMaxValue = a.getFloat(R.styleable.CircleSliderLayout_secondaryProgressMaxValue, this.secondaryProgressMaxValue);
        secondaryProgressWidth = a.getDimensionPixelSize(R.styleable.CircleSliderLayout_secondaryProgressWidth, secondaryProgressWidth);
        secondaryProgressTint = a.getColor(R.styleable.CircleSliderLayout_secondaryProgressTint, secondaryProgressTint);

        Drawable thumb = a.getDrawable(R.styleable.CircleSliderLayout_thumb);
        if (a.hasValue(R.styleable.CircleSliderLayout_thumbTint)) {
            thumbTint = a.getColorStateList(R.styleable.CircleSliderLayout_thumbTint);
        } else {
            thumbTint = null;
        }
        if (a.hasValue(R.styleable.CircleSliderLayout_thumbTintMode)) {
            thumbTintMode = parseTintMode(a.getInt(R.styleable.CircleSliderLayout_thumbTintMode, -1), thumbTintMode);
        } else {
            thumbTintMode = null;
        }

        touchZoneFactor = a.getFloat(R.styleable.CircleSliderLayout_touchZoneFactor, 1f);
        touchToSeekEnabled = a.getBoolean(R.styleable.CircleSliderLayout_touchToSeekEnabled, true);

        setProgressMaxValue(progressMaxValue);
        setSecondaryProgressMaxValue(secondProgressMaxValue);

        setProgressValue(progressValue);
        setSecondaryProgressValue(secondProgressValue);

        setThumb(thumb);

        a.recycle();
    }

    private PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        int measuredSize = 0;

        //Then the size of the square is the
        if (widthMode == MeasureSpec.EXACTLY && height == MeasureSpec.EXACTLY) {
            //First case, we are in EXACTLY, EXACTLY.
            measuredSize = Math.min(width, height);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            //Second case, only the width is in EXACTLY
            measuredSize = Math.min(width, height);
            if (measuredSize == width) {
                //Since we are not using all available height, we report a smaller height since heightMode is AT_MOST or UNDEFINED.
                height = measuredSize;
            }
        } else if (heightMode == MeasureSpec.EXACTLY) {
            //Second case, but only the height is in EXACTLY
            measuredSize = Math.min(width, height);
            if (measuredSize == height) {
                //Since we are not using all available width, we report a smaller width since widthMode is AT_MOST or UNDEFINED.
                width = measuredSize;
            }
        } else {
            //Last case, height and with are in AT_MOST or UNDEFINED
            measuredSize = Math.min(width, height);
            //We adjust both measures to the size of the internal square
            width = measuredSize;
            height = measuredSize;
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, widthMode),
                MeasureSpec.makeMeasureSpec(height, heightMode));

        //Finally we measure our children in exactly, exactly. They dont have much choice :)
        Rect childrenViewRect = getChildrenViewRect(new Rect());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(childrenViewRect.width(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(childrenViewRect.height(), MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Rect childrenViewRect = getChildrenViewRect(new Rect());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(childrenViewRect.left, childrenViewRect.top, childrenViewRect.right, childrenViewRect.bottom);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Rect childrenViewRect = getChildrenViewRect(new Rect());

        Path clippingPath = new Path();
        clippingPath.addCircle(childrenViewRect.centerX(), childrenViewRect.centerY(), childrenViewRect.width() / 2, Path.Direction.CW);

        if (!canvas.isHardwareAccelerated() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            dispatchDrawFullHardwareAccelerated(canvas, childrenViewRect, clippingPath);
        } else {
            dispatchDrawHardwareAcceleratedWithoutClipPath(canvas, childrenViewRect, clippingPath);
        }
    }

    private void dispatchDrawFullHardwareAccelerated(Canvas canvas, Rect childrenViewRect, Path clippingPath) {
        drawBorder(canvas, childrenViewRect);

        drawProgressTrackBackground(canvas, childrenViewRect, clippingPath);
        drawSecondaryProgressTrack(canvas, childrenViewRect, clippingPath);
        drawProgressTrack(canvas, childrenViewRect, clippingPath);
        drawThumb(canvas, childrenViewRect);

        drawClippedChildren(canvas, childrenViewRect, clippingPath);

        if (touchZoneVisible) {
            drawTouchZone(canvas, childrenViewRect);
        }
    }

    private void dispatchDrawHardwareAcceleratedWithoutClipPath(Canvas canvas, Rect childrenViewRect, Path clippingPath) {
        if (controlDrawingCacheDirty) {
            createControlDrawingCache(childrenViewRect, clippingPath);
        }
        drawClippedChildren(canvas, childrenViewRect, null);
        canvas.drawBitmap(controlDrawingCache, 0, 0, null);
    }

    private void createControlDrawingCache(Rect childrenViewRect, Path clippingPath) {
    }

    private void drawClippedChildren(Canvas canvas, Rect childrenViewRect, Path clippingPath) {
        canvas.save();

        if (clippingPath != null) {
            canvas.clipPath(clippingPath);
        }

        super.dispatchDraw(canvas);
        canvas.restore();
    }

    private void drawBorder(Canvas canvas, Rect childrenViewRect) {
        if (borderWidth <= 0) {
            float radius = childrenViewRect.width() / 2f + borderWidth;
            canvas.drawCircle(childrenViewRect.centerX(), childrenViewRect.centerY(), radius, borderPaint);
        }
    }

    private void drawProgressTrack(Canvas canvas, Rect childrenViewRect, Path clippingPath) {
        drawProgressTrack(canvas, childrenViewRect, clippingPath, progressWidth, progressValue,
                progressMaxValue, progressTrackPaint);
    }

    private void drawProgressTrackBackground(Canvas canvas, Rect childrenViewRect, Path clippingPath) {
        drawProgressTrack(canvas, childrenViewRect, clippingPath, progressWidth, 1f, 1f, progressTrackPaint);
    }

    private void drawSecondaryProgressTrack(Canvas canvas, Rect childrenViewRect, Path clippingPath) {
        drawProgressTrack(canvas, childrenViewRect, clippingPath, secondaryProgressWidth, secondaryProgressValue,
                secondaryProgressMaxValue, secondaryProgressTrackPaint);
    }

    private void drawProgressTrack(Canvas canvas, Rect childrenViewRect, Path clippingPath, int width, float value, float maxValue, Paint paint) {
        if (width >= 0 && value >= 0 && maxValue >= 0) {
            canvas.save();
            canvas.clipPath(clippingPath, Region.Op.DIFFERENCE);

            RectF progressRect = new RectF(childrenViewRect);
            progressRect.left -= borderWidth + width;
            progressRect.top -= borderWidth + width;
            progressRect.right += borderWidth + width;
            progressRect.bottom += borderWidth + width;

            float progressStartAngle = this.progressStartOffset - 90.0f;
            float progressAngle = (value / maxValue) * 360f;

            canvas.drawArc(progressRect, progressStartAngle, progressAngle, true, paint);
            canvas.restore();
        }
    }

    private void drawThumb(Canvas canvas, Rect childrenViewRect) {
        if (thumb != null) {
            Rect thumbRect = getThumbRect(tempRect);

            canvas.save();
            canvas.translate(thumbRect.left, thumbRect.top);
            thumb.draw(canvas);
            canvas.restore();
        }
    }

    private void drawTouchZone(Canvas canvas, Rect childrenViewRect) {
        updateTouchZone(childrenViewRect);

        canvas.save();

        touchZonePaint.setStrokeWidth((float) (touchZoneEndRadius - touchZoneStartRadius));
        canvas.drawCircle(childrenViewRect.centerX(), childrenViewRect.centerY(), (float) (touchZoneStartRadius + touchZoneEndRadius) / 2, touchZonePaint);

        canvas.restore();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (dragging == true) {
            //If the user is dragging the view, we intercept all touch events.
            return true;
        }
        if (!isEnabled()) {
            //If our view is not enabled, we intercept all touch event so the user can interract with
            //the children views.
            return true;
        }
        if (thumb == null) {
            //If no thumb is defined, this view is not touchable.
            return false;
        }

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            ev.getPointerCoords(0, tempCoords);
            if (isTouchEventInsideProgressTrack(tempCoords)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return true;
        }

        MotionEvent.PointerCoords coords = tempCoords;
        ev.getPointerCoords(0, coords);

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (touchToSeekEnabled || isTouchEventOnThumb(coords)) {
                    setPressed(true);
                    setDragging(true);
                    trackTouchEvent(ev, coords);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                if (dragging) {
                    trackTouchEvent(ev, coords);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_UP:
                if (dragging) {
                    progressAngle = normalizeAngle(progressAngle, getProgressStartOffsetAngle());
                    setPressed(false);
                    setDragging(false);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_CANCEL:
                if (dragging) {
                    setPressed(false);
                    setDragging(false);
                    return true;
                }
        }
        return false;
    }

    private void trackTouchEvent(MotionEvent ev, MotionEvent.PointerCoords coords) {
        Rect childrenViewRect = getChildrenViewRect(tempRect);

        double newAngle = getAngle(ev.getX(), ev.getY(), childrenViewRect);
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            double startOffsetAngle = getProgressStartOffsetAngle();
            newAngle = normalizeAngle(newAngle, startOffsetAngle);
        } else if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
            double direct = newAngle - progressAngle; //Direct comparaison
            double clockwise = newAngle - progressAngle + 2 * Math.PI; //Comparaison considering that one of the angle has done a full turn clockwise
            double counterClockwise = newAngle - progressAngle - 2 * Math.PI; //Comparaison considering that one of the angle has done a full turn counter clockwise

            double angularDifference = minAbs(direct, clockwise, counterClockwise);
            newAngle = progressAngle + angularDifference;
        }
        progressAngle = newAngle;

        float progressValue = getProgressValueFromAngle(progressAngle);
        setProgressValue(progressValue, true);
    }

    private double minAbs(double a, double... b) {
        double minAbs = Math.abs(a);
        double min = a;
        for (double v : b) {
            double newMinAbs = Math.min(minAbs, Math.abs(v));
            if (newMinAbs != minAbs) {
                minAbs = newMinAbs;
                min = v;
            }
        }
        return min;
    }

    private double getAngle(float touchX, float touchY, Rect childrenViewRect) {
        double centeredX = touchX - childrenViewRect.centerX();
        double centeredY = childrenViewRect.centerY() - touchY;

        double radius = Math.sqrt(Math.pow(centeredX, 2) + Math.pow(centeredY, 2));
        double cos = centeredX / radius;
        double sin = centeredY / radius;

        double angle = 0.0f;
        if (cos > 0) {
            angle = Math.asin(sin);
        } else if (cos == 0) {
            angle = ((sin > 0) ? 1 : -1) * Math.PI / 2;
        } else {
            angle = Math.asin(-sin) + Math.PI;
        }
        angle = 2 * Math.PI - (angle - Math.PI / 2); //Trigonometric angle do not rotate in the right sens. So we correct that and adjust our 0.

        return normalizeAngle(angle, 0f);
    }

    /**
     * Normalize the angle between [zeroOffset; zeroOffset + 2*Pi[ radian.
     */
    private double normalizeAngle(double angle, double zeroOffset) {
        while (angle < zeroOffset || angle >= zeroOffset + 2 * Math.PI) {
            angle += ((angle < zeroOffset) ? 1 : -1) * 2 * Math.PI;
        }
        return angle;
    }

    private float getProgressValueFromAngle(double angle) {
        double progressStartOffset = getProgressStartOffsetAngle();
        if (angle < progressStartOffset) {
            return 0.0f;
        } else if (angle >= progressStartOffset + 2 * Math.PI) {
            return progressMaxValue;
        }
        angle = normalizeAngle(angle - progressStartOffset, 0);
        return (float) (angle / (2 * Math.PI)) * progressMaxValue;
    }

    private double getProgressStartOffsetAngle() {
        return (progressStartOffset / 360d) * 2 * Math.PI;
    }

    //Those four methods must be overridden if you want your drawable to react to the
    //change of the view state: disabled, pressed, etc...
    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == thumb || super.verifyDrawable(who);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();

        if (thumb != null) {
            thumb.jumpToCurrentState();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (thumb != null && thumb.isStateful()) {
            thumb.setState(getDrawableState());
        }
    }

    @Override
    @SuppressLint("NewApi")
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);

        if (thumb != null) {
            thumb.setHotspot(x, y);
        }
    }

    /**
     * Returns the size of the internal square where everything is drawn.
     */
    private int getMeasuredSize() {
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        return Math.min(width, height);
    }

    /**
     * Returns the width used to draw the border, the progress bars and the thumb.
     */
    private int getControlWidth() {
        int progressesWidth = Math.max(progressWidth, secondaryProgressWidth);
        int width = borderWidth + progressesWidth;
        if (thumb != null) {
            //We assume that the thumb is symmetric.
            int thumbSize = Math.max(thumb.getIntrinsicWidth(), thumb.getIntrinsicHeight());
            if (thumbSize > progressesWidth) {
                width += (thumbSize - progressesWidth) / 2;
            }
        }
        return width;
    }

    /**
     * Returns the rectangle where we will draw everything.
     * The rest of the space will stay always blank.
     * The position of the rectangle depends on the gravity.
     */
    private Rect getRect(Rect rect) {
        int measuredSize = getMeasuredSize();
        rect.left = getPaddingLeft();
        rect.top = getPaddingTop();
        rect.right = measuredSize;
        rect.bottom = measuredSize;
        return rect;
    }

    /**
     * Returns the rectangle where we will draw all children views.
     * The position of the rectangle depends on the gravity.
     */
    private Rect getChildrenViewRect(Rect rect) {
        getRect(rect);
        int controlWidth = getControlWidth();
        rect.left += controlWidth;
        rect.top += controlWidth;
        rect.right -= controlWidth;
        rect.bottom -= controlWidth;
        return rect;
    }

    /**
     * Returns the rectangle where the thumb is being drawn or null if thumb
     * is null.
     */
    private Rect getThumbRect(Rect rect) {
        if (thumb == null) {
            return null;
        }
        Rect childrenViewRect = getChildrenViewRect(rect);

        float progressAngle = ((progressValue / progressMaxValue) * 2 * (float) Math.PI);
        float progressStartAngle = (float) (getProgressStartOffsetAngle() - Math.PI / 2);

        float angle = (float) normalizeAngle(progressAngle + progressStartAngle, 0f);

        int r = childrenViewRect.height() / 2 + borderWidth + progressWidth / 2;
        int xOffsetFromCenter = Math.round(r * (float) Math.cos(angle));
        int yOffsetFromCenter = Math.round(r * (float) Math.sin(angle));

        rect.left = childrenViewRect.centerX() + xOffsetFromCenter - thumb.getIntrinsicWidth() / 2;
        rect.top = childrenViewRect.centerY() + yOffsetFromCenter - thumb.getIntrinsicHeight() / 2;
        rect.bottom = rect.top + thumb.getIntrinsicHeight();
        rect.right = rect.left + thumb.getIntrinsicWidth();
        return rect;
    }

    private int getControlDrawingCacheBackgroundColor() {
        View view = this;
        int backgroundColor = Color.TRANSPARENT;
        while (view != null && backgroundColor == Color.TRANSPARENT) {
            if (view.getBackground() != null && view.getBackground() instanceof ColorDrawable) {
                backgroundColor = ((ColorDrawable) view.getBackground()).getColor();
            }
            if (view.getParent() != null && view.getParent() instanceof View) {
                view = (View)view.getParent();
            } else {
                view = null;
            }
        }
        // We remove the alpha component of any background color we have found
        return backgroundColor | 0x0FF000000;
    }

    private void updateTouchZone(Rect childrenViewRect) {
        touchZoneStartRadius = childrenViewRect.height() / 2d + borderWidth;
        touchZoneEndRadius = childrenViewRect.height() / 2d + borderWidth + progressWidth;

        if (thumb.getIntrinsicHeight() > progressWidth) {
            double thumbOverlay = (thumb.getIntrinsicHeight() - progressWidth) / 2;
            touchZoneStartRadius -= thumbOverlay;
            touchZoneEndRadius += thumbOverlay;
        }

        // We augment/reduce the size of the touchable track by the touch zone factor
        if (touchZoneFactor != 1f) {
            double touchZoneCenter = (touchZoneEndRadius + touchZoneStartRadius) / 2;
            double touchZoneSize = (touchZoneEndRadius - touchZoneStartRadius) * touchZoneFactor;

            touchZoneStartRadius = touchZoneCenter - touchZoneSize / 2;
            touchZoneEndRadius = touchZoneCenter + touchZoneSize / 2;
        }
    }

    private boolean isTouchEventInsideProgressTrack(MotionEvent.PointerCoords coords) {
        Rect childrenViewRect = getChildrenViewRect(tempRect);
        updateTouchZone(childrenViewRect);

        double x = coords.x - childrenViewRect.centerX();
        double y = coords.y - childrenViewRect.centerY();

        double r = Math.pow(x, 2) + Math.pow(y, 2);
        return r >= Math.pow(touchZoneStartRadius, 2) && r <= Math.pow(touchZoneEndRadius, 2);
    }

    private boolean isTouchEventOnThumb(MotionEvent.PointerCoords coords) {
        Rect thumbRect = getThumbRect(tempRect);

        return thumbRect.contains((int) coords.x, (int) coords.y);
    }

    /**
     * Recreates all paint that will be used to draw the layout.
     */
    private void invalidatePaints() {
        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(useAntiAliasing);

        progressTrackPaint = new Paint();
        progressTrackPaint.setColor(progressTint);
        progressTrackPaint.setAntiAlias(useAntiAliasing);

        progressTrackBackgroundPaint = new Paint();
        progressTrackBackgroundPaint.setColor(progressBackgroundColor);
        progressTrackBackgroundPaint.setAntiAlias(useAntiAliasing);

        secondaryProgressTrackPaint = new Paint();
        secondaryProgressTrackPaint.setColor(secondaryProgressTint);
        secondaryProgressTrackPaint.setAntiAlias(useAntiAliasing);

        if (touchZoneVisible) {
            touchZonePaint = new Paint();
            touchZonePaint.setStyle(Paint.Style.STROKE);
            touchZonePaint.setColor(Color.RED);
            touchZonePaint.setAlpha(127);
        }

        controlDrawingCachePaint = new Paint();
        controlDrawingCachePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @SuppressLint("NewApi")
    private void applyThumbTint() {
        if (thumb == null) {
            return;
        }

        if (thumbTint != null || thumbTintMode != null) {
            thumb = thumb.mutate();
            if (thumbTint != null) {
                thumb.setTintList(thumbTint);
            }
            if (thumbTintMode != null) {
                thumb.setTintMode(thumbTintMode);
            }
        }

        // The drawable (or one of its children) may not have been
        // stateful before applying the tint, so let's try again.
        if (thumb.isStateful()) {
            thumb.setState(getDrawableState());
        }
    }

    private void setDragging(boolean dragging) {
        if (this.dragging != dragging) {
            this.dragging = dragging;
            if (changeListener != null) {
                if (this.dragging) {
                    changeListener.onStartTrackingTouch(this);
                } else {
                    changeListener.onStopTrackingTouch(this);
                }
            }
        }

    }

    public void setProgressStartOffset(float progressStartOffset) {
        if (progressStartOffset < 0 || progressStartOffset >= 360) {
            throw new IllegalArgumentException("progressStartOffset must be an angle in degrees between 0 and 360 exclusive.");
        }
        if (this.progressStartOffset != progressStartOffset) {
            this.progressStartOffset = progressStartOffset;
            invalidate();
        }
    }

    public float getProgressStartOffset() {
        return progressStartOffset;
    }

    private void setProgressValue(float progressValue, boolean byUser) {
        if (progressValue < 0) {
            throw new IllegalArgumentException("Progress bar value or max value cannot be negative.");
        }
        if (progressValue > progressMaxValue) {
            progressValue = progressMaxValue;
        }
        if (this.progressValue != progressValue) {
            this.progressValue = progressValue;
            if (changeListener != null) {
                changeListener.onProgressChanged(this, progressValue, byUser);
            }
            invalidate();
        }
    }

    public void setProgressValue(float progressValue) {
        setProgressValue(progressValue, false);
    }

    public float getProgressValue() {
        return progressValue;
    }

    public void setProgressMaxValue(float progressMaxValue) {
        if (progressMaxValue < 0) {
            throw new IllegalArgumentException("Progress bar value or max value cannot be negative.");
        }
        if (this.progressMaxValue != progressMaxValue) {
            this.progressMaxValue = progressMaxValue;
            setProgressValue(this.progressValue, false);
            invalidate();
        }
    }

    public float getProgressMaxValue() {
        return progressMaxValue;
    }

    public void setSecondaryProgressValue(float secondaryProgressValue) {
        if (secondaryProgressValue < 0) {
            throw new IllegalArgumentException("Progress bar value or max value cannot be negative.");
        }
        if (secondaryProgressValue > secondaryProgressMaxValue) {
            secondaryProgressValue = secondaryProgressMaxValue;
        }
        if (this.secondaryProgressValue != secondaryProgressValue) {
            this.secondaryProgressValue = secondaryProgressValue;
            invalidate();
        }
    }

    public float getSecondaryProgressValue() {
        return secondaryProgressValue;
    }

    public void setSecondaryProgressMaxValue(float secondaryProgressMaxValue) {
        if (secondaryProgressMaxValue < 0) {
            throw new IllegalArgumentException("Progress bar value or max value cannot be negative.");
        }
        if (this.secondaryProgressMaxValue != secondaryProgressMaxValue) {
            this.secondaryProgressMaxValue = secondaryProgressMaxValue;
            setSecondaryProgressValue(this.secondaryProgressValue);
            invalidate();
        }
    }

    public float getSecondaryProgressMaxValue() {
        return secondaryProgressMaxValue;
    }

    public void setThumb(Drawable thumb) {
        if (this.thumb != null) {
            this.thumb.setCallback(null);
        }

        if (thumb != null) {
            thumb.setCallback(this);
            //We translate the canvas to draw the thumb, so we set the bounds here.
            thumb.setBounds(0, 0, thumb.getIntrinsicWidth(), thumb.getIntrinsicHeight());

            //If the size of the thumb change, we need to recalculate the layout so we can
            //resize properly our control and children views.
            if (this.thumb == null || this.thumb.getIntrinsicHeight() != thumb.getIntrinsicHeight() ||
                    this.thumb.getIntrinsicWidth() != thumb.getIntrinsicWidth()) {
                requestLayout();
            }
        }

        this.thumb = thumb;

        applyThumbTint();
        invalidate();
    }

    public OnCircleSliderLayoutChangeListener getOnCircleSliderLayoutChangeListener() {
        return changeListener;
    }

    public void setOnCircleSliderLayoutChangeListener(OnCircleSliderLayoutChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface OnCircleSliderLayoutChangeListener {
        void onProgressChanged(CircleSliderLayout circleSliderLayout, float progress, boolean fromUser);

        void onStartTrackingTouch(CircleSliderLayout circleSliderLayout);

        void onStopTrackingTouch(CircleSliderLayout circleSliderLayout);
    }
}
