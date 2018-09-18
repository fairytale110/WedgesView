package tech.nicesky.wedgesview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.widget.SeekBar;

import tech.nicesky.libwedgesview.WedgesView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WedgesView wedgesView = findViewById(R.id.wedgesView);
        AppCompatSeekBar seekBar = findViewById(R.id.seekBar);
        AppCompatTextView txtProgress = findViewById(R.id.txtProgress);

        wedgesView.setRotateSpeed(0.5F);
        txtProgress.setText(String.valueOf(wedgesView.getRotateSpeed()) + "F");
        seekBar.setProgress((int) (wedgesView.getRotateSpeed() * 10F));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtProgress.setText(String.valueOf(progress/10F) + "F");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                wedgesView.setRotateSpeed( seekBar.getProgress() / 10F);
            }
        });
    }

    public void test(){
        int[] colors = new int[4];
        colors[0] = Color.parseColor("#C2DFD7");
        colors[1] = Color.parseColor("#FFE6F5");
        colors[2] = Color.parseColor("#FE718D");
        colors[3] = Color.parseColor("#E90C59");
        WedgesView wedgesView = new WedgesView(this);
        wedgesView.setBackgroundColor(Color.WHITE);//Set View's background color
        wedgesView.setColors(colors);//set wedges's color
        wedgesView.setRotateSpeed(0.5F);//set wedges's fastest speed
        wedgesView.setWedgeAlpha(0.8F);//set wedges's alpha
        //set wedges diameter
        wedgesView.setWedgeDiameter((int) getResources().getDimension(R.dimen.dp_200));
        wedgesView.reStart();//begin anim
        //wedgesView.stop();// stop anim
    }
}
