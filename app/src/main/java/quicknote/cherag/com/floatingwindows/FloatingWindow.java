package quicknote.cherag.com.floatingwindows;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Cherag on 14-07-2015.
 */
public class FloatingWindow extends Service {
    LinearLayout ll,insider;
    WindowManager wm;
    private Button stop,save;
    EditText textInput;
    DataHandler dbHandler;

    private WindowManager.LayoutParams updatedParameters;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        ll = (LinearLayout) View.inflate(this, R.layout.windowlayout, null);
        insider=(LinearLayout) View.inflate(this,R.layout.button_layout_editor,null);
        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        ll.setBackgroundColor(Color.argb(66, 255, 0, 0));
        ll.setBackgroundResource(R.drawable.window_layout);
        ll.setLayoutParams(llParameters);
        insider.setOrientation(LinearLayout.HORIZONTAL);

        textInput = (EditText) View.inflate(this, R.layout.edittextlay, null);
//        setEditText();
        setStopButton();
        setsaveButton();

        ll.setOrientation(LinearLayout.VERTICAL);

        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(450, 450, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        ll.addView(textInput);
        insider.addView(stop);
        insider.addView(save);
        ll.addView(insider);
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

        textInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);

                } else {
                    Log.d("", "Lost focus");
                }
            }
        });

    }

    private void setsaveButton() {
        save=new Button(this);
        save.setText("Save");
        ViewGroup.LayoutParams contentParameters = new ViewGroup.LayoutParams(225, 60);
        save.setLayoutParams(contentParameters);
        save.setPadding(5,5,5,5);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FloatingWindow.this,"Hiii",Toast.LENGTH_SHORT).show();
                dbHandler=new DataHandler(getApplicationContext());
                dbHandler.open();
                dbHandler.insertData(textInput.getText().toString().substring(0), textInput.getText().toString());

            }
        });
    }

    private void setStopButton() {
        stop = new Button(this);
        stop.setText("Stop");
        stop.setPadding(10,5,5,5);

        ViewGroup.LayoutParams contentParameters = new ViewGroup.LayoutParams(225, 60);
        stop.setLayoutParams(contentParameters);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wm.removeView(ll);
                stopSelf();
            }
        });

    }
//
//    private void setEditText() {
//
//        textInput.setHint("Enter Text");
//        textInput.setFocusable(true);
//        textInput.setPadding(2, 20, 2, 5);
//        textInput.setClickable(true);
//        ViewGroup.LayoutParams contentParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 210);
//        textInput.setLayoutParams(contentParameters);
//        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//    }
//        textInput.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             updatedParameters.x=-10;
//                updatedParameters.y=-170;
//                wm.updateViewLayout(ll, updatedParameters);
////showVirtualKeyboard()  : shows keyboard but doesnot help in typing
//
//
//
//            }
//
//        });
//    }

//    private void showVirturalKeyboard() {
//        Timer timer = new Timer();
//        textInput.setText("Hello");
//        timer.schedule(new TimerTask() {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//            @Override
//            public void run() {
//
//                if (imm != null) {
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                    imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
//
//                }
//            }
//        }, 100);
//    }


}
