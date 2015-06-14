package com.example.alpha_linux.robotichandcontrol;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.microbridge.server.Server;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    ImageView imageView;

    private SeekBar servoBar00;
    private SeekBar servoBar01;
    private SeekBar servoBar02;
    private SeekBar servoBar03;



    //private OnCheckedChangeListener stateChangeListener = new StateChangeListener();
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener();

    Server server = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //To change images of hand in the screen
        imageView = (ImageView)findViewById(R.id.imageView);

        servoBar00 = (SeekBar) findViewById(R.id.SeekBarServo00);
        servoBar00.setTag((byte) 0x3);
        servoBar00.setOnSeekBarChangeListener(seekBarChangeListener);

        servoBar01 = (SeekBar) findViewById(R.id.SeekBarServo01);
        servoBar01.setTag((byte) 0x4);
        servoBar01.setOnSeekBarChangeListener(seekBarChangeListener);

        servoBar02 = (SeekBar) findViewById(R.id.SeekBarServo02);
        servoBar02.setTag((byte) 0x5);
        servoBar02.setOnSeekBarChangeListener(seekBarChangeListener);

        servoBar03 = (SeekBar) findViewById(R.id.SeekBarServo03);
        servoBar03.setTag((byte) 0x6);
        servoBar03.setOnSeekBarChangeListener(seekBarChangeListener);

        // Create new TCP Server
        try {
            server = new Server(4567);
            server.start();
        } catch (IOException e) {
            Log.e("microbridge", "Unable to start TCP server", e);
            System.exit(-1);
        }
    }



    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            byte portByte = (Byte) seekBar.getTag();
            //Toast.makeText(getApplicationContext(), " Moving finger ", Toast.LENGTH_SHORT).show();;
            try {
                server.send(new byte[] { portByte, (byte) progress });
            } catch (IOException e) {
                Log.e("microbridge", "problem sending TCP message", e);
            }

            //Changing the imageView

            if (progress == 0){
                imageView.setImageResource(R.drawable.handopen);
            }

            switch (seekBar.getId()){

                case R.id.SeekBarServo00:
                    imageView.setImageResource(R.drawable.hand0);
                    break;
                case R.id.SeekBarServo01:
                    imageView.setImageResource(R.drawable.hand1);
                    break;
                case R.id.SeekBarServo02:
                    imageView.setImageResource(R.drawable.hand2);
                    break;
                case R.id.SeekBarServo03:
                    imageView.setImageResource(R.drawable.hand3);
                    break;
                default:
                    switch (seekBar.getId()){
                        case R.id.SeekBarServo00:
                            imageView.setImageResource(R.drawable.handopen);
                            break;
                        case R.id.SeekBarServo01:
                            imageView.setImageResource(R.drawable.handopen);
                            break;
                        case R.id.SeekBarServo02:
                            imageView.setImageResource(R.drawable.handopen);
                            break;
                        case R.id.SeekBarServo03:
                            imageView.setImageResource(R.drawable.handopen);
                            break;
                    }
            }

        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }


        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    }

}