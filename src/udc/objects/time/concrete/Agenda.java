package udc.objects.time.concrete;

import udc.objects.enums.AgendaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Agenda {
    protected int id;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected AgendaType type;
    protected ArrayList<Agenda> exceptions;

    public Agenda () {
        this.setExceptions(new ArrayList<>());
    }

    public boolean isException (LocalDateTime ldt) {
        for (Agenda a : this.getExceptions())
            if (a.getStartTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"))
                    .equals(ldt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"))))
                return true;

        return false;
    }

    /**
     * Checks if the {@link Agenda} instance clashes with an already existing Agenda in the parameter.
     * @param agendas the list of agendas to check
     * @return true if the range clashes with an {@link Agenda} in the agendas list; else false
     */
    public static boolean clashes (ArrayList<Agenda> agendas, LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        for (Agenda a : agendas)
            if (a.getStartTime().format(dtf).equals(start.format(dtf)))
                if ((start.isAfter(a.getStartTime()) || start.isEqual(a.getStartTime())) && start.isBefore(a.getEndTime()))
                    return true;

        return false;
    }

    /**
     * Converts String into a LocalDateTime. String must follow a {@code yyyy/MM/dd hh:mm a} format (i.e. 1998/03/31 06:30 PM)
     * @param time - the String to parse into a LocalDateTime instance
     * @return the generated LocalDateTime instance
     */
    public static LocalDateTime strToTime (String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"));
    }

    /**
     * Converts a LocalDateTime to it's String equivalent in a format of {@code yyyy/MM/dd hh:mm a}
     * @param time - the LocalDateTime to convert
     * @return - the converted LocalDateTime
     */
    public static String timeToStr (LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a"));
    }

    /**
     * Creates an instance of a LocalDateTime based on the given parameters.
     * See {@link Agenda#toLDTime(int, int, int, int, int, String)}.
     * @param year String
     * @param month String
     * @param day String
     * @param hour int
     * @param minutes int
     * @param ampm String (am/pm)
     * @return
     */
    public static LocalDateTime toLDTime (String year, String month, String day, int hour, int minutes, String ampm) {
        return toLDTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), hour, minutes, ampm);
    }

    /**
     * Creates an instance of a LocalDateTime based on the given parameters.
     * @param year int
     * @param month int
     * @param day int
     * @param hour int
     * @param minutes int
     * @param ampm String (am/pm)
     * @return
     */
    public static LocalDateTime toLDTime (int year, int month, int day, int hour, int minutes, String ampm) {
        return LocalDateTime.of(year, month, day, hour, minutes).plusHours((ampm.toLowerCase().equals("pm")) ? ((hour == 12) ? 0 : 12) : 0);
    }

    /*** Getters and Setters ***/
    public AgendaType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public ArrayList<Agenda> getExceptions() {
        return exceptions;
    }

    public void setType(AgendaType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setExceptions(ArrayList<Agenda> exceptions) {
        this.exceptions = exceptions;
    }
}
