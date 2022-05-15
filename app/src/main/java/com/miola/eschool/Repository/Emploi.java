package com.miola.eschool.Repository;

import java.util.ArrayList;
import java.util.List;

public class Emploi {
    private long semester;
    private long week;

    public Emploi() {
    }

    public Emploi(long semester, long week) {
        this.semester = semester;
        this.week = week;
    }

    public long getSemester() {
        return semester;
    }

    public void setSemester(long semester) {
        this.semester = semester;
    }

    public long getWeek() {
        return week;
    }

    public void setWeek(long week) {
        this.week = week;
    }

}