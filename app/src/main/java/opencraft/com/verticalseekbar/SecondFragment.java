package opencraft.com.verticalseekbar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import opencraft.com.verticalseekbarlib.VerticalSeekBar;
import opencraft.com.verticalseekbarlib.VerticalSeekBarListener;

/**
 * Created by vsossella on 15/01/18.
 */

public class SecondFragment extends Fragment implements ViewTreeObserver.OnGlobalFocusChangeListener,
        ViewTreeObserver.OnDrawListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.second_fragment, container, false);


        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).setVerticalSeekBarListener(new VerticalSeekBarListener() {
            @Override
            public void onValueChanged(float value) {

            }

            @Override
            public void onYPositionChanged(float thumbY, float backgroundY) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.post(new Runnable() {
            @Override
            public void run() {
                ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).applyInitialAnimation(2500);
            }
        });
//        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).onWindowFocusChanged(true);

    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
//        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).applyInitialAnimation(2500);
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).applyInitialAnimation(2500);
    }

    @Override
    public void onDraw() {
        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).applyInitialAnimation(2500);
    }

    @Override
    public void onGlobalLayout() {
        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).applyInitialAnimation(2500);
    }


}
