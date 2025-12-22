package com.mycourse.app.mycourse.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycourse.app.mycourse.model.Course;


@Component
public class CourseFileDAO implements CourseDAO{

    private static final Logger LOG = Logger.getLogger(CourseFileDAO.class.getName());

    private Map<Integer,Course> courseCache; //Local cache

    private ObjectMapper objectMapper;
    private String filename;

    private static int nextId;

    public CourseFileDAO(@Value("${courses.file}") String filename, ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId(){
        int id = nextId;
        ++nextId;
        return id;

    }

    /**
     * Saves Courses from CourseCache into the Data File
     * @return True if successful, False if not
     * @throws IOException when file couldn't be accesed 
     */
    private boolean Save() throws IOException{
        Course[] courseArray = getCourseArray();
        objectMapper.writeValue(new File(filename),courseArray);
        return true;
    }

    /**
     * Loads Course Data from the Data file
     * @return True if successful, False if not
     * @throws IOException when file couldn't be accesed 
     */
    private boolean load() throws IOException{
        courseCache = new TreeMap<>();
        nextId = 0;
        try{
            Course[] courseArray = objectMapper.readValue(new File(filename),Course[].class );

            for(Course course: courseArray){
                courseCache.put(nextId,course);
                if (course.getId() > nextId)
                    nextId = course.getId();
            }
            return true;
        }
        catch( IOException e){
            LOG.log(Level.WARNING, "Load error:{0}",e);
            return false;
        }
    }
    
    /**
     * gets all courses that contain text from containsText
     * @param containsText text used to filter results, may be null
     * @return array of {@linkplain Course courses} 
     */
    private Course[] getCourseArray(String containsText){
        ArrayList<Course> courseArrayList = new ArrayList<>();

        for(Course course: courseCache.values()){
            if(containsText == null || course.getName().contains(containsText)){
                courseArrayList.add(course);
            }
        }
        Course[] courseArray = new Course[courseArrayList.size()];
        return courseArrayList.toArray(courseArray);
    }

    private Course[] getCourseArray(){
        return getCourseArray(null);
    }

    @Override
    public Course createCourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Course[] getAllCourses() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Course getCourse(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Course findCourse(String ContainText) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Course updateCourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean deleteCourse(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
