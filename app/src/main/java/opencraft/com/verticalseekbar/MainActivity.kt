package opencraft.com.verticalseekbar

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import opencraft.com.verticalseekbarlib.VerticalSeekBarListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

//        seekbar.setWantedValue(2500)
//
//        seekbar.setWantedValue(2500)
//        seekbar.setValue(5000)
//        seekbar.setMaxValue(2500)
//        seekbar.setStep(25)
//
//        seekbar.setVerticalSeekBarListener(object: VerticalSeekBarListener{
//            override fun onYPositionChanged(thumbY: Float, backgroundY: Float) {
//                background_top.y = backgroundY + top.height - 10
//            }
//
//            override fun onValueChanged(f: Float) {
//                text_top.text = (5000 - f).toString()
//                text_bottom.text = f.toString()
//            }
//
//        })

        setContentFragment(MainFragment())



    }

    private fun setContentFragment(fragment: Fragment) {
        val fragmentManager = fragmentManager
        fragmentManager.beginTransaction()
                .replace(fragment_content.id, fragment)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit()
    }

}
