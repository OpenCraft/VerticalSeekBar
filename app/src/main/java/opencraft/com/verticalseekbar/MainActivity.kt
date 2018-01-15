package opencraft.com.verticalseekbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import opencraft.com.verticalseekbarlib.VerticalSeekBarListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar.setVerticalSeekBarListener(object: VerticalSeekBarListener{
            override fun onYPositionChanged(thumbY: Float, backgroundY: Float) {
                background_top.y = backgroundY + top.height - 4
            }

            override fun onValueChanged(f: Float) {
                text_top.text = (5000 - f).toString()
                text_bottom.text = f.toString()
            }

        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            seekbar.applyInitialAnimation(2500)
        }
    }


}
