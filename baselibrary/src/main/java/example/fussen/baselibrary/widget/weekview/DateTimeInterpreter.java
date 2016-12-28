package example.fussen.baselibrary.widget.weekview;

import java.util.Calendar;

public interface DateTimeInterpreter {
    String interpretDate(Calendar date);
    String interpretWeek(Calendar date);
    String interpretMonth(Calendar date);
    String interpretTime(int hour);

}
