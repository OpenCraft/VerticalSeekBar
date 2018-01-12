package opencraft.com.verticalseekbarlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by vsossella on 09/01/18.
 */

public class VerticalSeekBar extends RelativeLayout {

    LayoutInflater inflater;
    View layoutView;
    View verticalSeekBarBackground;
    View verticalSeekBarThumb;
    VerticalSeekBarListener listener;
    float dY = 0;
    int marginTop = 0;
    int value = 0;
    int maxValue = 0;
    int step = 0;
    int calculatedValue = 0;
    final int DEFAULT_VALUE = 500;
    final int DEFAULT_STEP = 25;
    boolean hitBottom = false;
    float hitBottomY = 0;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
        loadCustomAttrs(context, attrs);
    }

    private void init() {
        layoutView = inflater.inflate(R.layout.vertical_seek_bar, this, true);
        verticalSeekBarThumb = layoutView.findViewById(R.id.agiplan_custom_seekbar_thumb);
        verticalSeekBarBackground = layoutView.findViewById(R.id.agiplan_custom_seekbar_background);
        addThumbTouchListener();
    }

    private void loadCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VerticalSeekBar,
                0, 0);
        loadBackgroundAttr(attributes);
        loadThumbAttr(attributes);
        loadValueAttr(attributes);
        loadStepAttr(attributes);
        loadMaxValueAttr(attributes);
        setupBackgroundMarginTopAttr(attributes);
    }

    private void loadValueAttr(TypedArray attributes) {
        value = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_value, DEFAULT_VALUE);
    }

    private void loadStepAttr(TypedArray attributes) {
        step = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_step, DEFAULT_STEP);
    }

    private void loadMaxValueAttr(TypedArray attributes) {
        maxValue = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_max_value, 0);
    }

    private void loadBackgroundAttr(TypedArray attributes) {
        int colorResourceId = attributes.getColor(R.styleable.VerticalSeekBar_seekbar_backgroundColor, 0);
        verticalSeekBarBackground.setBackgroundColor(colorResourceId);
    }

    private void loadThumbAttr(TypedArray attributes) {
        int thumbResourceId = attributes.getResourceId(R.styleable.VerticalSeekBar_seekbar_thumb, 0);
        ((ImageView) verticalSeekBarThumb).setImageResource(thumbResourceId);
    }

    private void setupBackgroundMarginTopAttr(TypedArray attributes) {
        marginTop = attributes.getDimensionPixelSize(R.styleable.VerticalSeekBar_seekbar_background_margin_top, 0);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, marginTop, 0, 0);
        verticalSeekBarBackground.setLayoutParams(layoutParams);
    }

    private void addThumbTouchListener() {
        verticalSeekBarThumb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int interations = value / step;
                int heightAreaLessMarginTop = getHeight() - marginTop;
                int pixelNumberToInteractionWithoutMargin = getHeight() / interations;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP: {
                        dY = view.getY() - event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        float wantedY = event.getRawY() + dY + verticalSeekBarThumb.getHeight() / 2;
                        float wantedYForBottom;
                        int multiplier;

                        if (hitBottom) {
                            wantedYForBottom = wantedY + marginTop;
                            multiplier = (int) wantedYForBottom / pixelNumberToInteractionWithoutMargin;
                            hitBottom = hitBottomY < wantedY;
                        } else {
                            multiplier = (int) wantedY / pixelNumberToInteractionWithoutMargin;
                        }

                        calculatedValue = value - (multiplier * step);
                        int interationsToGetMaxValue = maxValue / step;
                        float maxValueY = interationsToGetMaxValue * pixelNumberToInteractionWithoutMargin;

                        if (wantedY <= maxValueY || maxValueY == 0) {
                            handleIfHitTop(wantedY);
                            handleIfHitBottom(heightAreaLessMarginTop, wantedY);
                            handleIfIsBetweenTopAndBottom(heightAreaLessMarginTop, wantedY);

                            callOnValueChanged(calculatedValue);
                        } else {
                            callOnValueChanged(maxValue);
                            setYPosition(maxValueY, maxValueY - marginTop);
                        }
                        break;
                    }
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    private void callOnValueChanged(float value) {
        if (listener != null)
            listener.onValueChanged(value);
    }

    private void handleIfIsBetweenTopAndBottom(int heightArea, float wantedY) {
        if (wantedY > marginTop && wantedY <= heightArea) {
            setYPosition(wantedY, wantedY - marginTop);
        }
    }

    private void handleIfHitBottom(int heightArea, float wantedY) {
        if (wantedY - marginTop > heightArea) {
            hitBottom = true;
            hitBottomY = heightArea - marginTop;
            setYPosition(heightArea, heightArea - marginTop);
            calculatedValue = 0;
        }
    }

    private void handleIfHitTop(float wantedY) {
        if (wantedY + marginTop < marginTop) {
            setYPosition(marginTop, 0);
            calculatedValue = value;
        }
    }

    private void setYPosition(float yBackground, float yThumb) {
        verticalSeekBarBackground.setY(yBackground);
        verticalSeekBarThumb.setY(yThumb);
    }

    public void setVerticalSeekBarListener(VerticalSeekBarListener verticalSeekBarListener) {
        listener = verticalSeekBarListener;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
