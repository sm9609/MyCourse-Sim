package com.mycourse.app.mycourse.persistence;
import com.mycourse.app.mycourse.model.Course;

/**
 * Interface for CourseDAO(Data Access Object)
 * @author Samuel Mensah
 */
public interface  CourseDAO {
    
    public Course createCourse(Course course);

    public Course[] getAllCourses();

    public Course getCourse(int id);

    public Course findCourse(String ContainText);

    public Course updateCourse(Course course);

    public Boolean deleteCourse(int id);

    
}
