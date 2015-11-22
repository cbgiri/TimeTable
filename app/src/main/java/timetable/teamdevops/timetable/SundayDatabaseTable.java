package timetable.teamdevops.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by king on 17-11-2015.
 */
public class SundayDatabaseTable  {

    SundayTable sundayTable;
    Context context;
    public SundayDatabaseTable(Context context){
        sundayTable=new SundayTable(context);
        this.context=context;

    }
    public long insertdata(int to,int from,String des)
    {
        Message.message(context,"inside insert");
        //SQLiteDatabase db=sundayTable.getWritableDatabase();
        //ContentValues contentValues=new ContentValues();
        //contentValues.put(SundayTable.to,to);
        //contentValues.put(SundayTable.from,from);
        //contentValues.put(SundayTable.description,des);
        //long id=db.insert(SundayTable.TABLE_NAME,null,contentValues);
        return -1;
    }

    static class SundayTable extends SQLiteOpenHelper{
        private static final String DATABASE_NAME = "TimeTableDataBase";
        private static final String TABLE_NAME = "SUNADAY_TABLE";
        private static final int DATABASE_VERSION = 8;
        private static final String from = "from";
        private static final String to = "to";
        private static final String description = "description";
        private static final String CREATE_TABLE =

                "CREATE TABLE " + TABLE_NAME + " ("
                        + from + " INTEGER , "
                        + to + " INTEGER , "
                        + description + " VARCHAR(255), "
                        + "PRIMARY KEY(" + from + "," + to + "," + description + "));";

        private static final String DROP_TABLE = "DROP TABLE" + TABLE_NAME;
        private Context context;

        public SundayTable(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Message.message(context, "Constructor called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE TABLE_NAME (from INTEGER PRIMARY KEY ,to INTEGER PRIMARY KEY , description VARCHAR(255) PRIMARY KEY));
            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context, "Oncreate called");
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                Message.message(context, "Onupgrade called");
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }
        }
    }
}
