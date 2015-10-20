package quicknote.cherag.com.floatingwindows;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Cherag on 16-07-2015.
 */

public class FloatingWindowAudio extends Service {
    LinearLayout ll;

    WindowManager wm;

    private Button play, start, stop,exit;
    private WindowManager.LayoutParams updatedParameters;
    private static int recordCounter=0;
    MediaRecorder myMediaRecorder;
    private String outputFile=null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = (LinearLayout) View.inflate(this, R.layout.windowlayout, null);
        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(66, 255, 0, 0));
        ll.setLayoutParams(llParameters);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        setPlayButton();
        setStartButton();
        setStopButton();
        setExitButton();
        stop.setEnabled(true);
        play.setEnabled(true);
        start.setEnabled(true);

        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath()+"/myrecording"+recordCounter+".3gp";
        myMediaRecorder = new MediaRecorder();
        myMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myMediaRecorder.setOutputFile(outputFile);




        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(500, 150, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);

        ll.addView(start);
        ll.addView(stop);
        ll.addView(play);
        ll.addView(exit);
        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER | Gravity.CENTER;
        wm.addView(ll, parameters);


        ll.setOnTouchListener(new View.OnTouchListener() {


            {
                updatedParameters = parameters;
            }

            int x, y;
            float touchedX, touchedY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParameters.x;
                        y = updatedParameters.y;
                        touchedX = motionEvent.getRawX();
                        touchedY = motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = (int) (x + (motionEvent.getRawX() - touchedX));
                        updatedParameters.y = (int) (y + (motionEvent.getRawY() - touchedY));

                        wm.updateViewLayout(ll, updatedParameters);


                }

                return false;
            }
        });



    }

    private void setExitButton() {
        exit = new Button(this);
        exit.setMaxWidth(50);
        exit.setText("Exit");

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wm.removeView(ll);
                stopSelf();
            }
        });
    }


    private void setStopButton() {
        stop = new Button(this);
        stop.setMaxWidth(50);
       stop.setText("Stop");
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    Toast.makeText(FloatingWindowAudio.this,"Saved as"+outputFile,Toast.LENGTH_SHORT).show();
                    myMediaRecorder.stop();
                    myMediaRecorder.release();

                    myMediaRecorder=null;
                    stop.setEnabled(false);
                    play.setEnabled(true);


                }catch(Exception e){
                    e.printStackTrace();
                }
                start.setEnabled(true);
                play.setEnabled(true);
            }
        });

    }

    private void setPlayButton() {
        play = new Button(this);
        play.setText("Play");
        play.setMaxWidth(50);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Toast.makeText(FloatingWindowAudio.this,"Playing Audio",Toast.LENGTH_SHORT).show();
                    MediaPlayer m=new MediaPlayer();
                    m.setDataSource(outputFile);
                    m.prepare();
                    m.start();

                }catch(Exception e){
                    e.printStackTrace();
                }
                play.setEnabled(true);
                stop.setEnabled(true);
                Toast.makeText(FloatingWindowAudio.this,outputFile,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setStartButton() {
        start = new Button(this);
        start.setText("Start");
        start.setMaxWidth(50);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    recordCounter++;
                    outputFile= Environment.getExternalStorageDirectory().getAbsolutePath()+"/myrecording"+recordCounter+".3gp";
                    myMediaRecorder = new MediaRecorder();
                    myMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myMediaRecorder.setOutputFile(outputFile);

                    Toast.makeText(FloatingWindowAudio.this,"Recording started",Toast.LENGTH_SHORT).show();
                    myMediaRecorder.prepare();
                    myMediaRecorder.start();

                }catch(Exception e){
                    e.printStackTrace();
                }
                start.setEnabled(false);
                stop.setEnabled(true);
            }
        });
    }


}
