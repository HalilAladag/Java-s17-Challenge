package exceptions;

import model.Course;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class CourseValidation {

    public static void checkCourseValid(Course course){
        if((course.getCredit()<0||course.getCredit()>4)||
                course.getName()==null || course.getName().isEmpty()){
            throw new CourseException("Course credential are not valid", HttpStatus.BAD_REQUEST);
        }
    }
    public static void isIdValid(int id){
        if(id<0){
            throw new CourseException("Id is not valid", HttpStatus.BAD_REQUEST);
        }
    }
    public static void isDublicateNameFound(List<Course> courses, String name){
        Optional<Course> foundCourse = courses.stream().filter(course->course.getName().equals((name))).findFirst();
        if(foundCourse.isPresent()){
            throw new CourseException("Course with this name already exist: " + name, HttpStatus.BAD_REQUEST);
        }

    }
}
