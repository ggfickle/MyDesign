package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.Old_DeptDao;
import hfuu.examination.domain.Old_Dept;
import hfuu.examination.service.Old_DeptService;
@Service
public class Old_DeptServiceImpl implements Old_DeptService {
	@Resource
	private Old_DeptDao dao;
	
	@Override
	public List<Old_Dept> selDeptInfoByLimit(String dept,String college,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Old_Dept> depts=dao.selDeptInfoByLimit(dept,college);
		return depts;
	}

	@Override
	public Integer selCountByLimit(String dept,String college) {
		// TODO Auto-generated method stub
		return dao.selCountDeptInfoByLimit(dept,college);
	}

	@Override
	public Integer updDeptByEdit(Old_Dept dept) {
		// TODO Auto-generated method stub
		return dao.updDeptByEdit(dept);
	}

	@Override
	public int insDeptInfo(Old_Dept dept) {
		// TODO Auto-generated method stub
		return dao.insDeptInfo(dept);
	}

	@Override
	public boolean delOldDeptInfo(List<Integer> data) {
		// TODO Auto-generated method stub
		return dao.delOldDeptInfo(data)>0?true:false;
	}

}
