package ohtu;

import java.util.ArrayList;
import java.util.List;

public class Submission {

    private int week;
    private int hours;
    private List<Integer> exercises = new ArrayList<>();
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    private String listToCleanString() {
        if (exercises.size() > 0) {
            String ex = exercises.toString();
            return ex.substring(1, ex.length() - 1);
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return "" + course
                + ", week " + week
                + ", total exercises completed " + exercises.size()
                + ", time spent " + hours
                + ", completed exercises: " + listToCleanString() + "";
    }

}
