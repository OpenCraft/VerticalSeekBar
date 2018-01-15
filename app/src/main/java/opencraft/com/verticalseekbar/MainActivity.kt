package opencraft.com.verticalseekbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import opencraft.com.verticalseekbarlib.VerticalSeekBarListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar.setVerticalSeekBarListener(object : VerticalSeekBarListener {
            override fun onAnimationStop(thumbY: Float, backgroundY: Float) {
                balloon.y = thumbY - 15
                balloon.visibility = View.VISIBLE
            }

            override fun onYPositionChanged(thumbY: Float, backgroundY: Float) {
                background_top.y = backgroundY + top.height - 10
            }

            override fun onValueChanged(f: Float) {
                text_top.text = (5000 - f).toString()
                text_bottom.text = f.toString()
            }

        })

        seekbar.post { seekbar.applyInitialAnimation(2500) }
    }

}
