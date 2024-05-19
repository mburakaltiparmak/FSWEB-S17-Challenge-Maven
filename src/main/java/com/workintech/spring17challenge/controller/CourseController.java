package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.dto.ApiResponse;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.CourseGpa;
import com.workintech.spring17challenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private List<Course> courses;
    private CourseGpa lowCourseGpa;
    private CourseGpa mediumCourseGpa;
    private CourseGpa highCourseGpa;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") CourseGpa highCourseGpa){
        this.lowCourseGpa=lowCourseGpa;
        this.mediumCourseGpa=mediumCourseGpa;
        this.highCourseGpa=highCourseGpa;
    }
    @PostConstruct
    public void init(){
        this.courses=new ArrayList<>();
    }
    @GetMapping
    public List<Course> getAll(){
        return this.courses;
    }
    @GetMapping("/{name}")
    public Course getByName(@PathVariable String name){

        CourseValidation.checkName(name);
        for (Course course : courses) {
            if (course.getName().equalsIgnoreCase(name)) {
                return course;
            }
        }
        throw new ApiException("Course not found with name: " + name, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Course course){
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkName(course.getName());
        //CourseValidation.isRepetitiveCourse(courses, course.getId(), course.getName());
        courses.add(course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(course,totalGpa);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable("id") Integer id,@RequestBody Course course){
        CourseValidation.isRepetitiveCourse(courses, id, course.getName());
        CourseValidation.isIdValid(id);
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());
        Course existingCourse = getExistingCourseById(id);
        int indexOfExisting = courses.indexOf(existingCourse);
        courses.set(indexOfExisting,course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(courses.get(indexOfExisting),totalGpa);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Integer id){
        CourseValidation.isIdValid(id);
        Course existingCourse = getExistingCourseById(id);
        courses.remove(existingCourse);
    }
    private Integer getTotalGpa(Course course) {
        Integer totalGpa = null;
        if(course.getCredit() <= 2){
            totalGpa = course.getGrade().getCoefficient()*course.getCredit()*lowCourseGpa.getGpa();
        }
        else if (course.getCredit() == 3){
            totalGpa = course.getGrade().getCoefficient()*course.getCredit()*mediumCourseGpa.getGpa();
        }
        else {
            totalGpa = course.getGrade().getCoefficient()*course.getCredit()*highCourseGpa.getGpa();
        }
        return totalGpa;
    }
    private Course getExistingCourseById(Integer id){
        return courses.stream()
                .filter(course1 -> course1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiException("course not found with id " + id, HttpStatus.NOT_FOUND));

    }
}
