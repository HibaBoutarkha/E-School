package com.miola.eschool.Repository;

public class Semester {
    private long semester;

    public Semester() {
    }

    public Semester(long semester) {
        this.semester = semester;
    }

    public long getSemester() {
        return semester;
    }

    public void setSemester(long semester) {
        this.semester = semester;
    }

    public String toString(){
        return "{semester = "+getSemester()+"}";
    }
}
