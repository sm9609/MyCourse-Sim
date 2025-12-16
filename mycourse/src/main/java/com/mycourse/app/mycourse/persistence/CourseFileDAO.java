package com.mycourse.app.mycourse.persistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycourse.app.mycourse.model.Course;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class CourseFileDAO implements CourseDAO{

    private static final Logger LOG = Logger.getLogger(CourseFileDAO.class.getName());

    private Map<Integer,Course> Courses;

    private ObjectMapper Mapper;

    public CourseFileDAO(@Value("${courses.file}")){
        
    }
}
