package com.mycourse.app.mycourse.persistence;
import java.io.IOException;

import com.mycourse.app.mycourse.model.Course;

/**
 * Interface for CourseDAO(Data Access Object)
 * @author Samuel Mensah
 */
public interface  CourseDAO {
    
    public Course createCourse(Course course) throws IOException;

    public Course[] getAllCourses() throws IOException;

    public Course getCourse(int id) throws IOException;

    public Course[] findCourse(String containsText) throws IOException;

    public Course updateCourse(Course course) throws IOException;

    public Boolean deleteCourse(int id) throws IOException;

    
}
