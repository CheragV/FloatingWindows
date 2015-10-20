package quicknote.cherag.com.floatingwindows;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cherag on 15-07-2015.
 */
public class DataHandler {
    public static final String ID = "_id";
    public static final String KEY = "key";
    public static final String KEY_TEXT = "textnote";
    public static final String TABLE_NAME = "notes";
    public static final String DATA_BASE_NAME = "mydatabase1";
    public static final int DATABASE_VERSION = 2;
    public static final String ALL_KEYS = " ";

    String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY + " TEXT, "
            + KEY_TEXT + " TEXT" + ");";
    public static final String TABLE_CREATE = "create table notes(_id INTEGER AUTOINCREMENT, key text not null,textnote text not null);";
    DataBaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;

    public DataHandler(Context ctx) {
        this.ctx = ctx;
        dbhelper = new DataBaseHelper(ctx);

    }

    public DataHandler open() {

        db = dbhelper.getWritableDatabase();
        return this;
    }

    public DataHandler close() {
        dbhelper.close();
        return this;
    }

    public long insertData(String key, String note) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEXT, note);
        initialValues.put(KEY, key);


        return db.insert(TABLE_NAME, null, initialValues);
    }

    public Cursor getAllRows() {
        String where = null;
        SQLiteDatabase db =dbhelper.getReadableDatabase();

        Cursor c = db.query(TABLE_NAME, new String[]{ID, KEY, KEY_TEXT}, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        db.close();
        return c;

    }


    private class DataBaseHelper extends SQLiteOpenHelper {


        public DataBaseHelper(Context ctx) {
            super(ctx, DATA_BASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_CONTACTS_TABLE);
            } catch (SQLException e) {
                e.getStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);

        }
    }

}
