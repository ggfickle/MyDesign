package hfuu.examination.service.impl;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.Hfuu_TeacherDao;
import hfuu.examination.domain.Arrangement;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.Hfuu_TeacherService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class Hfuu_TeacherServiceImpl implements Hfuu_TeacherService {

    @Resource
    private Hfuu_TeacherDao hfuu_teacherMapper;

    @Override
    public int selCid(String name) {
        return hfuu_teacherMapper.selCid(name);
    }

    @Override
    public int insTeacherInfo(@Param("list") List<Hfuu_Teacher> list) {
        return hfuu_teacherMapper.insTeacherInfo(list);
    }

    @Override
    public List<Hfuu_Teacher> selIsTeacher(Hfuu_Teacher hfuu_teacher) {
        return hfuu_teacherMapper.selIsTeacher(hfuu_teacher);
    }

	@Override
	public String selTid(String name, String sname) {
		// TODO Auto-generated method stub
		return hfuu_teacherMapper.selTid(name, sname);
	}
	 @Override
	public String selTeacherIdByName(String name) {
	    return hfuu_teacherMapper.selTeacherIdByName(name);
    }

	@Override
	public Integer updAddChiefById(String id) {
		
		return hfuu_teacherMapper.updTeacherAddChiefById(id);
	}

	@Override
	public Integer updAddDeputyById(String id) {
		// TODO Auto-generated method stub
		return hfuu_teacherMapper.updTeacherAddDeputyById(id);
	}
	@Override
	public List<Hfuu_Teacher> getArrangementInfo() {
		List<Hfuu_Teacher> arrangementInfoList = hfuu_teacherMapper.getArrangementInfo();
		for(int i=0;i<arrangementInfoList.size();i++) {
			Hfuu_Teacher hfuu_Teacher = arrangementInfoList.get(i);
			List<Arrangement> mainArrangementList = hfuu_Teacher.getMainArrangement();
			for(int j=0;j<mainArrangementList.size();j++) {
				Arrangement mainArrangement = mainArrangementList.get(j);
				String[] timeArr = mainArrangement.getTime().split(":");
				mainArrangement.setTime("----第"+timeArr[0]+"周-星期"+timeArr[1]+"-第"+timeArr[2]+"节----");
				mainArrangementList.set(j, mainArrangement);
			}
			hfuu_Teacher.setMainArrangement(mainArrangementList);
			
			List<Arrangement> secondArrangementList = hfuu_Teacher.getSecondArrangement();
			for(int j=0;j<secondArrangementList.size();j++) {
				Arrangement secondArrangement = secondArrangementList.get(j);
				String[] timeArr = secondArrangement.getTime().split(":");
				secondArrangement.setTime("----第"+timeArr[0]+"周-星期"+timeArr[1]+"-第"+timeArr[2]+"节----");
				secondArrangementList.set(j, secondArrangement);
			}
			hfuu_Teacher.setSecondArrangement(secondArrangementList);
			
			arrangementInfoList.set(i, hfuu_Teacher);
		}
		return arrangementInfoList;
	}

	@Override
	public Hfuu_Teacher selTeacherInfoById(String id) {
		return hfuu_teacherMapper.selTeacherInfoById(id);
	}

	@Override
	public Integer updTeacherEmailById(Hfuu_Teacher teacher) {
		return hfuu_teacherMapper.updTeacherEmailById(teacher);
	}

	@Override
	public List<Hfuu_Teacher> getArrangementInfoLimit(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return getArrangementInfo();
	}

	@Override
	public Integer selTeacherCount() {
		// TODO Auto-generated method stub
		return hfuu_teacherMapper.selTeacherCount();
	}

	@Override
	public List<Map<String, Integer>> selFrequency(String id) {
		List<Map<String, Integer>> data= hfuu_teacherMapper.selFrequency();
		data.addAll(hfuu_teacherMapper.selFrequencyByTeacher(id));
		return data;
	}

}
