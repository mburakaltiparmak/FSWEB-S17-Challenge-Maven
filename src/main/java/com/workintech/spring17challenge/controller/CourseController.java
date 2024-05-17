package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.model.Course;
import com.workintech.spring17challenge.entity.HighCourseGpa;
import com.workintech.spring17challenge.entity.LowCourseGpa;
import com.workintech.spring17challenge.entity.MediumCourseGpa;
import com.workintech.spring17challenge.model.CourseGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private Map<Integer, Course> courses;
    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;
    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa){
        this.lowCourseGpa=lowCourseGpa;
        this.mediumCourseGpa=mediumCourseGpa;
        this.highCourseGpa=highCourseGpa;
    }
    @PostConstruct
    public void init(){
        this.courses=new HashMap<>();
    }
    @GetMapping
    public List getCourseList(){
        return new ArrayList<>(this.courses.values());
    }
    @GetMapping("/{name}")
    public Course getCourseName(@PathVariable("name") String name){
        //Validation gelecek
        return courses.get(name);
    }
    @PostMapping
    public Course saveCourse(@RequestBody Course course){
        //Validation gelecek (Response değeri 201)

        this.courses.put(course.getId(),course);
        return courses.get(course.getId());
    }
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable("id") Integer id,@RequestBody Course course){
        //Validation gelecek
        if(courses.containsKey(id)){
            this.courses.put(id,course);
            return courses.get(id);
        }
        else {
            return saveCourse(course);
        }
    }
    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable("id") Integer id){
        //Validation gelecek
        return courses.remove(id);
    }
}
