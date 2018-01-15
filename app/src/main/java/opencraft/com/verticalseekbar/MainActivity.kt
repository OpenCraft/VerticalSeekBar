package opencraft.com.verticalseekbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        seekbar.setVerticalSeekBarListener { f ->
            run {
                text_top.text = (5000 - f).toString()
                text_bottom.text = f.toString()
            }
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            seekbar.applyInitialAnimation(2500)
        }
    }


}
