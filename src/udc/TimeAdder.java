package udc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class TimeAdder {
    String startString, endString;
    LocalTime timeStart, timeEnd;

    public TimeAdder() {
        startString = "7:30 AM";
        endString = "11:00 PM";
        DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
        timeStart = LocalTime.parse(startString, dtf);
        timeEnd = LocalTime.parse(endString, dtf);
    }

    public void add() {
        int x, y, a, b;
        double difference;
        x = timeStart.getHour();
        y = timeStart.getMinute();
        a = timeEnd.getHour();
        b = timeEnd.getMinute();
        if(b-y < 0) {
            difference = a-x;
            difference-=0.5;
        } else if(b-y > 0) {
            difference = a-x;
            difference+=0.5;
        } else {
            difference = a-x;
        }
        System.out.println(difference);
    }

    public static void main(String[] args) {
        TimeAdder adder = new TimeAdder();
        adder.add();
    }
}
