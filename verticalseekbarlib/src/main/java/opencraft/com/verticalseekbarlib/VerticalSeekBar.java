package opencraft.com.verticalseekbarlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
    int thumbMarginTop = 0;
    int value = 0;
    int maxValue = 0;
    int step = 0;
    float calculatedValue = 0;
    final int DEFAULT_VALUE = 500;
    final int DEFAULT_STEP = 25;
    int animationDuration = 0;

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
        loadAnimationDurationAttr(attributes);
        loadThumbAttr(attributes);
        loadValueAttr(attributes);
        loadStepAttr(attributes);
        loadMaxValueAttr(attributes);
        setupThumbMarginTopAttr(attributes);
    }

    private void loadValueAttr(TypedArray attributes) {
        value = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_value, DEFAULT_VALUE);
    }

    private void loadStepAttr(TypedArray attributes) {
        step = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_step, DEFAULT_STEP);
    }

    private void loadMaxValueAttr(TypedArray attributes) {
        maxValue = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_max_value, value);
    }

    private void loadAnimationDurationAttr(TypedArray attributes) {
        animationDuration = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_animation_duration, 0);
    }

    private void loadBackgroundAttr(TypedArray attributes) {
        int colorResourceId = attributes.getColor(R.styleable.VerticalSeekBar_seekbar_backgroundColor, 0);
        verticalSeekBarBackground.setBackgroundColor(colorResourceId);
    }

    private void loadThumbAttr(TypedArray attributes) {
        int thumbResourceId = attributes.getResourceId(R.styleable.VerticalSeekBar_seekbar_thumb, 0);
        ((ImageView) verticalSeekBarThumb).setImageResource(thumbResourceId);
    }

    private void setupThumbMarginTopAttr(TypedArray attributes) {
        thumbMarginTop = attributes.getDimensionPixelSize(R.styleable.VerticalSeekBar_seekbar_thumbMarginTop, 0);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(0, thumbMarginTop, 0, 0);
        verticalSeekBarThumb.setLayoutParams(layoutParams);
    }

    private void addThumbTouchListener() {
        verticalSeekBarThumb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP: {
                        dY = view.getY() - event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        float wantedY = event.getRawY() + dY - thumbMarginTop;
                        animateViews(wantedY);
                        break;
                    }
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    private int calculatePixelToInteraction() {
        int interations = value / step;
        return getHeight() / interations;
    }

    public void applyInitialAnimation(int wantedValue) {
        int pixelNumberToInteractionWithoutMargin = calculatePixelToInteraction();

        int interationsToGetWantedValue = wantedValue / step;
        float wantedValueY = interationsToGetWantedValue * pixelNumberToInteractionWithoutMargin;
        AnimatorSet animSetViews = new AnimatorSet();

        for (int i = (int) verticalSeekBarBackground.getY(); i <= wantedValueY; i++) {
            final int finalYPosition = i;
            calculateValueFromYPosition(i, pixelNumberToInteractionWithoutMargin);
            ObjectAnimator animVerticalSeekBarThumb = ObjectAnimator.ofFloat(verticalSeekBarThumb, "y", i + thumbMarginTop);
            ObjectAnimator animVerticalSeekBarBackground = ObjectAnimator.ofFloat(verticalSeekBarBackground, "y", i);
            animSetViews.playTogether(animVerticalSeekBarBackground, animVerticalSeekBarThumb);
            animSetViews.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    addMarginToBackground(finalYPosition);
                    callOnValueChanged(calculatedValue);
                    listener.onAnimationStop(verticalSeekBarThumb.getY(),
                            verticalSeekBarBackground.getY());
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);

                }
            });
            listener.onYPositionChanged(finalYPosition + thumbMarginTop, finalYPosition);
        }

        animSetViews.setDuration(animationDuration);
        animSetViews.start();
    }

    private void calculateValueFromYPosition(int yPosition, int pixelNumberToInteractionWithoutMargin) {
        float multiplier = yPosition / pixelNumberToInteractionWithoutMargin;
        calculatedValue = value - (multiplier * step);
    }

    private void animateViews(float wantedY) {
        int pixelNumberToInteractionWithoutMargin = calculatePixelToInteraction();
        calculateValueFromYPosition((int) verticalSeekBarBackground.getY(), pixelNumberToInteractionWithoutMargin);
        int interationsToGetMaxValue = maxValue / step;
        float maxValueY = interationsToGetMaxValue * pixelNumberToInteractionWithoutMargin;

        if (wantedY <= maxValueY && wantedY >= 0) {
            setYPosition(wantedY, wantedY);
            callOnValueChanged(calculatedValue);
        } else {
            if (wantedY >= maxValueY) {
                callOnValueChanged(value == maxValue ? 0 : maxValue);
                setYPosition(maxValueY, maxValueY);
            }
            if (wantedY <= 0) {
                callOnValueChanged(value);
                setYPosition(0, 0);
            }
        }
    }

    private void callOnValueChanged(float value) {
        if (listener != null)
            listener.onValueChanged(value);
    }

    private void setYPosition(float yBackground, float yThumb) {
        float accurateYThumb = yThumb + thumbMarginTop;
        verticalSeekBarBackground.setY(yBackground);
        verticalSeekBarThumb.setY(accurateYThumb);
        addMarginToBackground(yBackground);
        listener.onYPositionChanged(accurateYThumb, yBackground);
    }

    public View getVerticalSeekBarBackground() {
        return verticalSeekBarBackground;
    }

    public View getVerticalSeekBarThumb() {
        return verticalSeekBarThumb;
    }

    private void addMarginToBackground(float yBackground) {
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                getHeight() - (int) yBackground);
        verticalSeekBarBackground.setLayoutParams(layoutParams);
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
