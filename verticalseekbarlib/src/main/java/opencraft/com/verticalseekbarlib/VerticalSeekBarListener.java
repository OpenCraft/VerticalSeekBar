package opencraft.com.verticalseekbarlib;

/**
 * Created by vsossella on 10/01/18.
 */

public interface VerticalSeekBarListener {

    void onValueChanged(float value);
    void onYPositionChanged(float thumbY, float backgroundY);
    void onAnimationStop(float thumbY, float backgroundY);

}
