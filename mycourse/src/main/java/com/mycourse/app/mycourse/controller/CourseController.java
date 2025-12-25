package com.mycourse.app.mycourse.controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycourse.app.mycourse.model.Course;
import com.mycourse.app.mycourse.persistence.CourseDAO;


/**
 * Handles REST API calls for course recourses
 * @author Samuel Mensah
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
    private static final Logger LOG = Logger.getLogger(CourseController.class.getName());
    private final CourseDAO courseDAO;


    public CourseController(CourseDAO courseDAO){
        this.courseDAO = courseDAO;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
        LOG.log(Level.INFO, "POST /courses {0}", course);
        try{
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return new ResponseEntity<>(courseDAO.createCourse(course),header,HttpStatus.OK);
        }
        catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
