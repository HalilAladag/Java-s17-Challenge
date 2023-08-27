package entity;


import model.*;

public class CourseDetailFactory {

    public static CourseDetail createCourseDetail(Course course, CourseGpa low, CourseGpa medium, CourseGpa high){
        if(course.getCredit()<=2){
            return new CourseDetail(course,course.getCredit()*course.getGrade().getCoefficient()* ((LowCourseGpa)low).getGpa());
        }else if(course.getCredit()==3){
            return new CourseDetail(course, course.getCredit()*course.getGrade().getCoefficient()*((MediumCourseGpa) medium).getGpa());
        }else if(course.getCredit()==4){
            return new CourseDetail(course, course.getCredit()*course.getGrade().getCoefficient()*((HighCourseGpa) high).getGpa());
        }
        return null;
    }
}
