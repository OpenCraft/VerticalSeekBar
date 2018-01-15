package opencraft.com.verticalseekbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class MainFragment extends Fragment implements ViewTreeObserver.OnGlobalFocusChangeListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.main_fragment, container, false);

//        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).setVerticalSeekBarListener(new VerticalSeekBarListener() {
//            @Override
//            public void onValueChanged(float value) {
//
//            }
//
//            @Override
//            public void onYPositionChanged(float thumbY, float backgroundY) {
//
//            }
//        });

        view.findViewById(R.id.text_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(new SecondFragment());
            }
        });

        return view;
    }

    private void setContentFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
//        ((VerticalSeekBar) view.findViewById(R.id.seekbar_fragment)).applyInitialAnimation(2500);
    }
}
