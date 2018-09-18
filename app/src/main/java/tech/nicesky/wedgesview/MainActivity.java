package tech.nicesky.wedgesview;

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
}
