package quicknote.cherag.com.floatingwindows;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    Button b,getSavedTexts,startAudioRrecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button) findViewById(R.id.start);

        getSavedTexts=(Button) findViewById(R.id.getSavedTexts);
        
        b=(Button) findViewById(R.id.start);
        getSavedTexts=(Button) findViewById(R.id.getSavedTexts);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this,FloatingWindow.class));
            }
        });
        getSavedTexts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Notes.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"HII",Toast.LENGTH_SHORT).show();
            }
        });
        startAudioRrecorder=(Button) findViewById(R.id.startAudioRecorder);

        startAudioRrecorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Button Working",Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, FloatingWindowAudio.class));
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
