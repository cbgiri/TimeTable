package timetable.teamdevops.timetable;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
    String desc;
    int hour_1,min_1,hour_2,min_2;
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
    protected TimePickerDialog.OnTimeSetListener kTimePickerListner1=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    TimePicker x=view;
                    hour_1=hourOfDay;
                    min_1=minute;
                    Toast.makeText(MainActivity.this, hour_1 + ":" + min_1, Toast.LENGTH_SHORT).show();
                }
            };
    protected TimePickerDialog.OnTimeSetListener kTimePickerListner2=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    TimePicker x=view;
                    hour_1=hourOfDay;
                    min_1=minute;
                    Toast.makeText(MainActivity.this, hour_1 + ":" + min_1, Toast.LENGTH_SHORT).show();
                }
            };
    @Override
    public void onDialogMessage(String message) {
        if(message != "-1")
        {
            desc=message;
            TimePickerDialog d1=new TimePickerDialog(MainActivity.this,kTimePickerListner1,hour_1,min_1,false);
            d1.setTitle("to");
            d1.show();
            TimePickerDialog d2=new TimePickerDialog(MainActivity.this,kTimePickerListner2,hour_2,min_2,false);
            d2.setTitle("to");
            d2.show();
            int pos = viewPager.getCurrentItem();
            Toast.makeText(getApplicationContext(),pos+" fuck yeah",Toast.LENGTH_SHORT).show();

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