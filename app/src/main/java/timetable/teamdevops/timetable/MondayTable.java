package timetable.teamdevops.timetable;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by king on 17-11-2015.
 */
public class MondayTable extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="TimeTableDataBase";
    private static final String TABLE_NAME="MONDAY_TABLE";
    private static final int DATABASE_VERSION=1;
    private static final String from="from";
    private static final String to="to";
    private static final String description="description";
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+from+" INTEGER PRIMARY KEY ,"+to+" INTEGER PRIMARY KEY , "+description+" VARCHAR(255) PRIMARY KEY));";
    private static final String DROP_TABLE="DROP TABLE"+TABLE_NAME+"IF EXISTS";
    private Context context;
    public MondayTable(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
        Message.message(context,"Constructor called");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE TABLE_NAME (from INTEGER PRIMARY KEY ,to INTEGER PRIMARY KEY , description VARCHAR(255) PRIMARY KEY));
        try{
            db.execSQL(CREATE_TABLE);
            Message.message(context, "Oncreate called");
        }catch (SQLException e){
            Message.message(context,""+e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL(DROP_TABLE);
            Message.message(context,"Onupgrade called");
            onCreate(db);
        }catch (SQLException e) {
            Message.message(context,""+e);
        }
    }
}
