package timetable.teamdevops.timetable;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by king on 17-11-2015.
 */
public class Message {
    public static  void message(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
