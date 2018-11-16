package ohtu;

import java.util.ArrayList;

public class Course {

    private String name;
    private String term;
    private int year;
    private String fullName;
    private ArrayList<Integer> exercises;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return fullName + " " + term + " " + year;
    }
}
