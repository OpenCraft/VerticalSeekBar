package opencraft.com.verticalseekbarlib;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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
    float dX = 0, dY = 0;
    int marginTop = 0;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
        loadAttrs(context, attrs);
    }

    private void init() {
        layoutView = inflater.inflate(R.layout.vertical_seek_bar, this, true);
        verticalSeekBarThumb = layoutView.findViewById(R.id.agiplan_custom_seekbar_thumb);
        verticalSeekBarBackground = layoutView.findViewById(R.id.agiplan_custom_seekbar_background);

        addThumbTouchListener();
    }

    private void loadAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VerticalSeekBar,
                0, 0);



        int colorResourceId = attributes.getColor(R.styleable.VerticalSeekBar_seekbar_backgroundColor, 0);
        verticalSeekBarBackground.setBackgroundColor(colorResourceId);


        int thumbResourceId = attributes.getResourceId(R.styleable.VerticalSeekBar_seekbar_thumb, 0);
        ((ImageView) verticalSeekBarThumb).setImageResource(thumbResourceId);

        marginTop = attributes.getDimensionPixelSize(R.styleable.VerticalSeekBar_seekbar_background_margin_top, 10);

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

                float heightArea = getHeight() - marginTop;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:

                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        float yToMove  = event.getRawY() + dY;

                        if (yToMove < marginTop) {
                            verticalSeekBarBackground.setY(marginTop);
                            verticalSeekBarThumb.setY(0);
                        }
                        if (yToMove > heightArea) {
                            verticalSeekBarBackground.setY(heightArea);
                            verticalSeekBarThumb.setY(heightArea - marginTop);
                        }

                        if (yToMove > marginTop && yToMove <= heightArea) {

                            Log.d("MOVING", String.format("Y do componente: %f - DY: %f - event.getRawY(): %f - yToMove: %f", getY(), dY, event.getRawY(), yToMove));
                            verticalSeekBarBackground.setY(yToMove);
                            verticalSeekBarThumb.setY(yToMove-marginTop);
                        }


                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }


}
