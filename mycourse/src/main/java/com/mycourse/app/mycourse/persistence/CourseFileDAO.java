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

    private  Map<Integer,Course> courseCache; //Local cache

    private ObjectMapper objectMapper;
    private String filename;

    private static int nextId;

    public CourseFileDAO(@Value("${courses.file}") String filename, ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    // <-----------------------  SUPPORT FUNCTIONS --------------------------> //

    private synchronized static int nextId(){
        int id = nextId;
        ++nextId;
        return id;

    }

    /**
     * Saves {@linkplain Course course} from the courseCache into a file as an 
     * array of JSON objects
     * @return True if successful, False if not
     * @throws IOException when file couldn't be accesed 
     */
    private boolean save() throws IOException{
        Course[] courseArray = getCourseArray();
        objectMapper.writeValue(new File(filename),courseArray);
        return true;
    }

    /**
     * Loads Course Data from the Data file into a course array then assigns an
     * Id to each {@linkplain Course course} then puts them into a treeMap
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
     * Generates an array of {@linkplain Course course} from the CourseCache
     * @return The array of {@link Course course}, may be empty
     */
    private Course[] getCourseArray(){
        return getCourseArray(null);
    }

    /**
     * Generates an array of {@linkplain Course course} from the CourseCache 
     * for any {@linkplain Course course} that contains the text specified by
     * containsText
     * <br>
     * If containsText is Null, the array will consist of all {@linkplain Course courses}
     * in the CourseCache
     * @param containsText Specifided text used to filter the array, may be null
     * @return array of {@link Course courses} 
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

    // <-----------------------  ACCESS FUNCTIONS --------------------------> //

    /**
     * {@inheritDoc }
     */
    @Override
    public Course createCourse(Course course) throws IOException {
        synchronized(courseCache){
            if (findCourse(course.getName()).length == 0){
                Course newCourse = new Course(
                    nextId(),
                    course.getName(),
                    course.getInstructor(),
                    course.getCredit()
                );
                courseCache.put(newCourse.getId(),newCourse);
                save();
                return newCourse;
            }
            return null;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Course[] getAllCourses() throws IOException{
        return getCourseArray(null);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Course getCourse(int id) throws IOException{
        synchronized(courseCache){
            return courseCache.get(id);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Course[] findCourse(String containsText) throws IOException{
        synchronized(courseCache){
            return getCourseArray(containsText);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Course updateCourse(Course course) throws IOException{
        synchronized(courseCache){
            if(!courseCache.containsKey(course.getId())){
                return null;
            }

            Course updated = new Course(
                course.getId(),
                course.getName(), 
                course.getInstructor(), 
                course.getCredit()
            );
            courseCache.put(course.getId(), updated);
            save();
            return updated;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Boolean deleteCourse(int id) throws IOException{
        synchronized(courseCache){
            Course removed = courseCache.remove(id);
            if (removed == null){
                return false;
            }
            save();
            return true;
        }
    }
}
