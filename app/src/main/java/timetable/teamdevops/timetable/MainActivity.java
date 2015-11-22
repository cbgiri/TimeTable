package timetable.teamdevops.timetable;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DescriptionDialog.Communicator{

    private ViewPager viewPager;
    public int hour_1,min_1,hour_2,min_2;
    int callCount1=0;
    int callCount2=0;
    String desc;
    TimePickerDialog d1;
    TimePickerDialog d2;
    SundayDatabaseTable sundayTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManagar = getSupportFragmentManager();
        viewPager.setAdapter(new PagerAdapter(fragmentManagar));
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DescriptionDialog descDialog = new DescriptionDialog();
                descDialog.show(manager, "");
            }
        });
    }


    protected TimePickerDialog.OnTimeSetListener kTimePickerListner2=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour_2 = hourOfDay;
                    min_2 = minute;
                    if(callCount1==2) {

                        Toast.makeText(MainActivity.this, hour_2 + ":" + min_2 + "in timepicker2", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), viewPager.getCurrentItem() + "cool", Toast.LENGTH_SHORT).show();
                        int first_time_picker_value=hour_1*24+min_1;
                        int second_time_picker_value=hour_2*24+min_2;
                        Toast.makeText(getApplicationContext(),first_time_picker_value+" "+second_time_picker_value,Toast.LENGTH_SHORT).show();
                        if(first_time_picker_value>=second_time_picker_value)
                        {
                            Toast.makeText(getApplicationContext(),"Incorrect input format",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int pos = viewPager.getCurrentItem();

                            if(pos==0)
                            {
                                Message.message(getApplicationContext(),"it's sunday baby");
                               // long id=sundayTable.insertdata(first_time_picker_value,second_time_picker_value,desc);
                                if(id<0)
                                {
                                    Message.message(getApplicationContext(),"Not properly inserted");
                                }
                                else
                                {
                                    Message.message(getApplicationContext(),"Properly inserted");
                                }
                            }

                        }
                    }
                    callCount1++;
                    d2.dismiss();
                }
            };

    protected TimePickerDialog.OnTimeSetListener kTimePickerListner1=
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Message.message(getApplicationContext()," in timePicker1"+callCount2);
                    hour_1 = hourOfDay;
                    min_1 = minute;
                    if(callCount2==2) {

                        Toast.makeText(MainActivity.this, hour_1 + ":" + min_1 + "in timepicker1", Toast.LENGTH_SHORT).show();

                        d2=new TimePickerDialog(MainActivity.this,kTimePickerListner2,hour_2,min_2,false);
                        d2.setTitle("to");
                        d2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(getApplicationContext(), "Dialog 2 dismissed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        d2.show();

                    }
                    callCount2++;
                }
            };

    public void show()
    {
        Toast.makeText(getApplicationContext(),"Show Dialog 1", Toast.LENGTH_SHORT).show();
        d1=new TimePickerDialog(MainActivity.this,kTimePickerListner1,hour_1,min_1,false);
        d1.setTitle("from");
        d1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(),"Dialog 1 dismissed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        d1.show();
    }
    @Override
    public void onDialogMessage(String message) {
        if(message != "-1")
        {
            callCount1=1;
            callCount2=1;
            desc=message;
            show();

        }
    }
}


class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        if (position == 0)
            frag = new FragmentA();
        if (position == 1)
            frag = new FragmentB();
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String str = null;
        if (position == 0)
            str =  "Monday";
        if ( position == 1)
            str = "Tuesday";
        return str;
    }
}