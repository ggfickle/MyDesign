package hfuu.examination.service.impl;

import org.springframework.stereotype.Service;

import hfuu.examination.dao.Hfuu_CourseDao;
import hfuu.examination.domain.upload.Hfuu_Course;
import hfuu.examination.service.Hfuu_CourseService;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Hfuu_CourseServiceImpl implements Hfuu_CourseService {

	@Resource
	private Hfuu_CourseDao hfuu_courseMapper;

	@Override
	public int insCourseInfo(List<Hfuu_Course> list) {
		return hfuu_courseMapper.insCourseInfo(list);
	}

	@Override
	public List<Hfuu_Course> isCourse(Hfuu_Course hfuu_course) {
		return hfuu_courseMapper.isCourse(hfuu_course);
	}

	/**
	 * 判断课程表中是否已存在date_addr信息
	 */
	@Override
	public boolean selCourseByOther(Hfuu_Course course) {
		return hfuu_courseMapper.selCourseByOther(course) == 0 ? true : false;
	}

	/**
	 * 获取课程时间和cid
	 */
	@Override
	public List<Map<String, Object>> selByTidAndSname(String tid, String sname) {
		// TODO Auto-generated method stub
		return hfuu_courseMapper.selByTidAndSname(tid, sname);
	}

	@Override
	public String selDateAddrByCidAndSid(int cid, String sid, String t_id) {
		return hfuu_courseMapper.selDateAddrByCidAndSid(cid, sid, t_id);
	}

	@Override
	public List<Hfuu_Course> selCourseCodeAndAttre(Hfuu_Course hfuu_course) {
		return hfuu_courseMapper.selCourseCodeAndAttre(hfuu_course);
	}

	@Override
	public int updCourseCodeAndAtre(Hfuu_Course data) {
		return hfuu_courseMapper.updateUsersBySname(data);
	}

	@Override
	public List<Map<String, String>> selCourseNumberByCollege() {
		List<Map<String, String>> data=hfuu_courseMapper.selCoursesNumber();
		if(data.isEmpty()) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("name","学院课程数");
			map.put("value","0");
			data.add(map);
		}
		return data;
	}

}
