package com.workintech.spring17challenge.entity;

import com.workintech.spring17challenge.model.CourseGpa;

public class LowCourseGpa implements CourseGpa {

    @Override
    public int getGpa() {
        return 3;
    }
}
