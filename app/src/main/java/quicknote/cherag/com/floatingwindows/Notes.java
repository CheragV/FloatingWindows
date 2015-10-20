package quicknote.cherag.com.floatingwindows;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Cherag on 15-07-2015.
 */
public class Notes extends ListActivity {
    DataHandler dbHandler;
    ListView list;
    TextView innertext;
    String text;
    TextView notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofnotes);
        list=(ListView) findViewById(android.R.id.list);
        populateListView();
//        notes=(TextView) findViewById(R.id.text);
//        notes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setContentView(R.layout.textview);
//                innertext=(TextView)  findViewById(R.id.text);
//
//                innertext.setText(notes.getText().toString());
//
//            }
//        });



    }
    public void populateListView(){
        dbHandler=new DataHandler(getApplicationContext());
        dbHandler.open();
        Cursor cursor=dbHandler.getAllRows();
        String[] fromFeildNames=new String[]{dbHandler.KEY,dbHandler.KEY_TEXT};
        text=new String(dbHandler.KEY_TEXT);
        int[] toViewIds= new int[]{R.id.key,R.id.note};
        SimpleCursorAdapter myCursorAdapter=new SimpleCursorAdapter(getBaseContext(),R.layout.textviews,cursor,fromFeildNames,toViewIds,0);
        setListAdapter(myCursorAdapter);
        dbHandler.close();

    }


}
