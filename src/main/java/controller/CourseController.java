package controller;

import entity.CourseDetail;
import entity.CourseDetailFactory;
import exceptions.CourseException;
import exceptions.CourseValidation;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    private List<Course> courses = new ArrayList<>();
    private CourseGpa LowGpa;
    private CourseGpa MediumGpa;
    private CourseGpa HighGpa;

    @Autowired
    public CourseController(
            @Qualifier("lowCourseGpa") CourseGpa LowGpa,
            @Qualifier("midCourseGpa") CourseGpa MediumGpa,
            @Qualifier("highCourseGpa") CourseGpa HighGpa) {
        this.LowGpa = LowGpa;
        this.MediumGpa = MediumGpa;
        this.HighGpa = HighGpa;
    }

    @GetMapping("/")
    public List<Course> get() {
        return courses;
    }
    @GetMapping("/{name}")
    public Course getByName(@PathVariable String name) {
        List<Course> requestedCourse = courses.stream().filter(course -> course.getName().equals(name)).collect(Collectors.toList());
        if (requestedCourse.isEmpty()) {
            throw new CourseException("Course with given name is not exist: " + name, HttpStatus.BAD_REQUEST);
        }
        return requestedCourse.get(0);
    }
    @PostMapping("/")
    public CourseDetail create(@RequestBody Course course){
        CourseValidation.isIdValid(course.getId());
        CourseValidation.checkCourseValid(course);
        CourseValidation.isDublicateNameFound(courses, course.getName());

        courses.add(course);
        return CourseDetailFactory.createCourseDetail(course,LowGpa,MediumGpa,HighGpa);
    }
    @PutMapping("/{id}")
    public Course update(@RequestBody Course course,@PathVariable int id){
        CourseValidation.checkCourseValid(course);
        Optional<Course> foundCourse = courses.stream().filter(c->c.getId()==id).findFirst();
        if(foundCourse.isPresent()) {
            int index = courses.indexOf(foundCourse.get());
            course.setId(id);
            courses.set(index, course);
            return course;
        } else{
            throw new CourseException("Course with given id is not exist: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public Course delete (@PathVariable int id){
        Optional<Course>foundCourse=courses.stream().filter(course->course.getId()==id).findFirst();
        if(foundCourse.isPresent()){
            int index = courses.indexOf(foundCourse.get());
            courses.remove(index);
            return foundCourse.get();
        }else{
            throw new CourseException("Course with given id is not exist: " + id, HttpStatus.BAD_REQUEST);
        }
    }


}