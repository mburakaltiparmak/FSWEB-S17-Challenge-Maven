package com.workintech.spring17challenge.validation;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.entity.Course;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CourseValidation {
    public static void isIdValid(Integer id){
        if(id == null || id < 0){
            throw new ApiException("Id is not valid : " + id, HttpStatus.BAD_REQUEST);
        }
    }
    public static void isRepetitiveCourse(List<Course> courses, Integer id, String name){
        for(Object object : courses){
            if(object instanceof Course){
                Course existingCourse = (Course) object;
                if (!existingCourse.getId().equals(id) && existingCourse.getName().equals(name)) {
                    throw new ApiException("You can't add this course, because its already exist : " + name, HttpStatus.BAD_REQUEST);
                }
            }

        }
    }
    public static void checkName(String name){
        if(name == null || name.isEmpty()){
            throw new ApiException("name cannot be null or empty : " + name ,HttpStatus.BAD_REQUEST);
        }
    }
    public static void checkCredit(Integer credit){
        if(credit < 0 || credit > 4 || credit == null ){
            throw new ApiException("Credit cannot lower than 0, higher than 4 and be null : " + credit,HttpStatus.BAD_REQUEST);
        }
    }
}
