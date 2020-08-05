package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.HCollegeDao;
import hfuu.examination.domain.HCollege;
import hfuu.examination.service.HCollegeService;
/**
 * 
 * @ClassName: HCollegeServiceImpl 
 * @Description: 学院服务实现类
 * @author llm
 * @date 2020-03-28 04:22:18
 */
@Service
public class HCollegeServiceImpl implements HCollegeService {
	@Resource
	private HCollegeDao dao;
	
	@Override
	public List<HCollege> selCollegeInfo() {
		return dao.selCollegeInfo("");
	}

	@Override
	public boolean selCollegeInfoIsExist(HCollege college) {
		// TODO Auto-generated method stub
		return dao.selCollegeInfoIsExist(college)>0?true:false;
	}

	@Override
	public boolean insCollegeInfo(HCollege college) {
		// TODO Auto-generated method stub
		return dao.insCollegeInfo(college)>0?true:false;
	}

	@Override
	public boolean delCollegeInfo(List<Integer> data) {
		return dao.delCollegeInfoBatch(data)>0?true:false;
	}

	@Override
	public boolean updCollegeInfo(HCollege college) {
		return dao.updCollegeInfo(college)>0?true:false;
	}

	@Override
	public List<HCollege> selCollegeInfoLimit(String name,Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return dao.selCollegeInfo(name);
	}

	@Override
	public Integer selCountCollegeInfo(String name) {
		// TODO Auto-generated method stub
		return dao.selCountOfCollege(name);
	}

	@Override
	public int selIdByName(String name) {
		
		return dao.selIdByName(name);
	}

	@Override
	public String selCollegeName(String id) {
	
		return dao.selColleegeName(id);
	}

	

}
