package com.bawp.myapplication.data;

import java.util.ArrayList;

public class CourseData {
    private String[] courseNames = {"SpangeBob One", "SpangeBob Two", "SpangeBob Three"};


    public ArrayList<Course> courseList() {
        ArrayList<Course> list = new ArrayList<>();
        for(int i = 0; i < courseNames.length; i++) {
            Course coures  = new Course(courseNames[i], courseNames[i].replace(" ", "").toLowerCase());
            list.add(coures);
        }
        return list;
    };

}
