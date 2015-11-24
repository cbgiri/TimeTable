package timetable.teamdevops.timetable.helper;

import java.util.Comparator;

import timetable.teamdevops.timetable.entities.TimeTableData;

/**
 * Created by- Chandra Bhan Giri
 */
public class MyComparator implements Comparator<TimeTableData> {

    @Override
    public int compare(TimeTableData t1, TimeTableData t2) {
        if (t1.getFrom_time() == t2.getFrom_time())
            return ((t1.getTo_time() < t2.getTo_time()) ? -1 : 1);
        return ((t1.getFrom_time() < t2.getFrom_time()) ? -1 : 1);
    }
}