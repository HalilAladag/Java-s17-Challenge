package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Course;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {
    private Course course;
    private double totalGpa;
}
