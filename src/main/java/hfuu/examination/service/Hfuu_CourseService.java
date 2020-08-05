package hfuu.examination.service;


import java.util.List;
import java.util.Map;

import hfuu.examination.domain.upload.Hfuu_Course;

public interface Hfuu_CourseService {

    int insCourseInfo(List<Hfuu_Course> list);

    List<Hfuu_Course> isCourse(Hfuu_Course hfuu_course);
    
    boolean selCourseByOther(Hfuu_Course course);
    
    List<Map<String, Object>> selByTidAndSname(String tid,String sname);
    
    String selDateAddrByCidAndSid(int cid,String sid,String t_id);
    

    List<Hfuu_Course> selCourseCodeAndAttre(Hfuu_Course hfuu_course);

    int updCourseCodeAndAtre(Hfuu_Course data);
    
    List<Map<String, String>> selCourseNumberByCollege();
}
