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
    View layoutView, verticalSeekBarBackground, verticalSeekBarThumb;
    VerticalSeekBarListener listener;
    float dY = 0;
    int marginTop = 0, valor = 500, step = 25, calculatedValue = 0;

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
        setupBackgroundMarginTopAttr(attributes);
    }

    private void loadValueAttr(TypedArray attributes) {
        valor = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_value, 500);
    }

    private void loadStepAttr(TypedArray attributes) {
        step = attributes.getInteger(R.styleable.VerticalSeekBar_seekbar_step, 25);
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

                int interations = valor / step;
                int heightArea = getHeight() - marginTop;
                int pixelNumberToInteraction = heightArea / interations;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP: {
                        dY = view.getY() - event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        float wantedY = event.getRawY() + dY;
                        int multiplier = (int) wantedY / pixelNumberToInteraction;
                        calculatedValue = valor - (multiplier * step);

                        handleIfHitTop(wantedY);
                        handleIfHitBottom(heightArea, wantedY);
                        handleIfIsBetweenTopAndBottom(heightArea, wantedY);

                        if (listener != null)
                            listener.onValueChanged(calculatedValue);
                        break;
                    }
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    public void setVerticalSeekBarListener(VerticalSeekBarListener verticalSeekBarListener) {
        listener = verticalSeekBarListener;
    }

    private void handleIfIsBetweenTopAndBottom(int heightArea, float wantedY) {
        if (wantedY > marginTop && wantedY <= heightArea) {
            setYPosition(wantedY, wantedY - marginTop);
            verticalSeekBarBackground.setY(wantedY);
            verticalSeekBarThumb.setY(wantedY - marginTop);
        }
    }

    private void handleIfHitBottom(int heightArea, float wantedY) {
        if (wantedY > heightArea) {
            setYPosition(heightArea, heightArea - marginTop);
            calculatedValue = 0;
        }
    }

    private void handleIfHitTop(float wantedY) {
        if (wantedY < marginTop) {
            setYPosition(marginTop, 0);
            calculatedValue = valor;
        }
    }

    private void setYPosition(float yBackground, float yThumb) {
        verticalSeekBarBackground.setY(yBackground);
        verticalSeekBarThumb.setY(yThumb);
    }

}
