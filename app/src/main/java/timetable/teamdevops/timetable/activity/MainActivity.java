package timetable.teamdevops.timetable.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;

import timetable.teamdevops.timetable.R;
import timetable.teamdevops.timetable.adapter.TimeTableAdapter;
import timetable.teamdevops.timetable.dialog.DescriptionDialog;
import timetable.teamdevops.timetable.entities.TimeTableData;
import timetable.teamdevops.timetable.fragment.FragmentFri;
import timetable.teamdevops.timetable.fragment.FragmentMon;
import timetable.teamdevops.timetable.fragment.FragmentSat;
import timetable.teamdevops.timetable.fragment.FragmentSun;
import timetable.teamdevops.timetable.fragment.FragmentThurs;
import timetable.teamdevops.timetable.fragment.FragmentTues;
import timetable.teamdevops.timetable.fragment.FragmentWed;
import timetable.teamdevops.timetable.helper.SQLiteHandler;

public class MainActivity extends AppCompatActivity implements DescriptionDialog.DialogCommunicator, TimeTableAdapter.DialogCommunicator1{

    private ViewPager viewPager;
    int viewPagerPosition = -1;
    public static String DAY[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new PagerAdapter(fragmentManager));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescriptionDialog();
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, TimeTableData data) {
        showFromTimePickerDialog(data);
    }

    private void showDescriptionDialog() {
        DialogFragment descDialog = new DescriptionDialog();
        descDialog.show(getSupportFragmentManager(), "Description dialog");
    }

    private void showFromTimePickerDialog(final TimeTableData data) {
        TimePickerDialog fromDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                data.setFrom_time(hourOfDay * 60 + minute);
                showToTimePickerDialog(data);
            }
        }, 0, 0, false);
        fromDialog.setTitle("TIME : FROM");
        fromDialog.show();
    }

    private void showToTimePickerDialog(final TimeTableData data) {
        TimePickerDialog toDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                data.setTo_time(hourOfDay * 60 + minute);
                insertDataToDatabase(data);
            }
        }, 0, 0, false);
        toDialog.setTitle("TIME : TO");
        toDialog.show();
    }

    private void insertDataToDatabase(TimeTableData data) {
        viewPagerPosition = viewPager.getCurrentItem();
        String dayTitle = DAY[viewPagerPosition];
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        db.addTimeTableEntry(dayTitle, data);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() == 0 && page != null) {
            ((FragmentMon)page).populateRecyclerView();
        }
    }

    @Override
    public void onPositiveClick(TimeTableData data) {
        deleteToDatabase(data);
    }
    private void deleteToDatabase(TimeTableData data) {
        viewPagerPosition = viewPager.getCurrentItem();
        String dayTitle = DAY[viewPagerPosition];
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        db.deleteTimeTableEntry(dayTitle, data);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() == 0 && page != null) {
            ((FragmentMon)page).populateRecyclerView();
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
                frag = new FragmentMon();
            if (position == 1)
                frag = new FragmentTues();
            if (position == 2)
                frag = new FragmentWed();
            if (position == 3)
                frag = new FragmentThurs();
            if (position == 4)
                frag = new FragmentFri();
            if (position == 5)
                frag = new FragmentSat();
            if (position == 6)
                frag = new FragmentSun();
            return frag;
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DAY[position];
        }
    }
}