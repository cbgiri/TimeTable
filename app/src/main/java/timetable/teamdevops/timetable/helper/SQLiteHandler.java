package timetable.teamdevops.timetable.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import timetable.teamdevops.timetable.activity.MainActivity;
import timetable.teamdevops.timetable.entities.TimeTableData;

/**
 * Created by- Chandra Bhan Giri
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mnnit";

    // Table Names
    private static String MON_TABLE = MainActivity.DAY[0];
    private static String TUES_TABLE = MainActivity.DAY[1];
    private static String WED_TABLE = MainActivity.DAY[2];
    private static String THURS_TABLE = MainActivity.DAY[3];
    private static String FRI_TABLE = MainActivity.DAY[4];
    private static String SAT_TABLE = MainActivity.DAY[5];
    private static String SUN_TABLE = MainActivity.DAY[6];

    // ALL DAY Column Names
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_CODE = "code";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_INSTRUCTOR = "instructor";
    private static final String KEY_FROM="fromTime";
    private static final String KEY_TO="toTime";

    private static  final String CREATE_MON_TABLE =
            "CREATE TABLE " + MON_TABLE + "("

                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";
    private static  final String CREATE_TUES_TABLE =
            "CREATE TABLE " + TUES_TABLE + "("
                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";
    private static  final String CREATE_WED_TABLE =
            "CREATE TABLE " + WED_TABLE + "("
                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";
    private static  final String CREATE_THURS_TABLE =
            "CREATE TABLE " + THURS_TABLE + "("
                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";
    private static  final String CREATE_FRI_TABLE =
            "CREATE TABLE " + FRI_TABLE + "("
                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";
    private static  final String CREATE_SAT_TABLE =
            "CREATE TABLE " + SAT_TABLE + "("
                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";

    private static  final String CREATE_SUN_TABLE =
            "CREATE TABLE " + SUN_TABLE + "("
                    + KEY_SUBJECT + " TEXT, "
                    + KEY_CODE + " TEXT, "
                    + KEY_VENUE + " TEXT, "
                    + KEY_INSTRUCTOR + " TEXT, "
                    + KEY_FROM + " INTEGER, "
                    + KEY_TO + " INTEGER "
                    + ")";

    private Context context;

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_MON_TABLE);
        db.execSQL(CREATE_TUES_TABLE);
        db.execSQL(CREATE_WED_TABLE);
        db.execSQL(CREATE_THURS_TABLE);
        db.execSQL(CREATE_FRI_TABLE);
        db.execSQL(CREATE_SAT_TABLE);
        db.execSQL(CREATE_SUN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MON_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TUES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WED_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + THURS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FRI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SAT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUN_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing timetable entry
     */
    public void addTimeTableEntry(String database_table , TimeTableData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT, data.getSubject());
        values.put(KEY_CODE, data.getCode());
        values.put(KEY_VENUE, data.getVenue());
        values.put(KEY_INSTRUCTOR, data.getInstructor());
        values.put(KEY_FROM, data.getFrom_time());
        values.put(KEY_TO, data.getTo_time());

        // Inserting Row
        long id = db.insert(database_table, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id + " in table " + database_table);
        Toast.makeText(context , "ENTRY ADDED!", Toast.LENGTH_SHORT).show();
    }
    public void deleteTimeTableEntry(String database_table,TimeTableData data){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_QUERY = " DELETE from " + database_table + " WHERE "
                                           + KEY_CODE + " = '" + data.getCode()
                                 + "' and " +KEY_SUBJECT+ " = '" + data.getSubject()
                                 + "' and " +KEY_VENUE+ " = '" + data.getVenue()
                                 + "' and " +KEY_INSTRUCTOR+ " = '" + data.getInstructor() + "'";
        db.execSQL(DELETE_QUERY);

    }
    /**
     * Get timetable data from tables
     */
    public ArrayList<TimeTableData> getTimeTableData(String database_table) {
        ArrayList<TimeTableData> timeTableData = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + database_table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount() ; i++) {
            TimeTableData entry = new TimeTableData();
            entry.setSubject(cursor.getString(0));
            entry.setCode(cursor.getString(1));
            entry.setVenue(cursor.getString(2));
            entry.setInstructor(cursor.getString(3));
            entry.setFrom_time(cursor.getInt(4));
            entry.setTo_time(cursor.getInt(5));
            timeTableData.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        // return user
        return timeTableData;

    }


}
