package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.HDeptDao;
import hfuu.examination.domain.Hdept;
import hfuu.examination.service.HDeptService;
@Service
public class HDeptServiceImpl implements HDeptService {
	@Resource
	private HDeptDao dao;
	
	@Override
	public List<Hdept> selDeptInfoByLimit(String major,String college,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Hdept> depts=dao.selDeptInfoByLimit(major,college);
		return depts;
	}

	@Override
	public Integer selCountByLimit(String major,String college) {
		// TODO Auto-generated method stub
		return dao.selCountDeptInfoByLimit(major,college);
	}

	@Override
	public Integer updDeptByEdit(Hdept dept) {
		// TODO Auto-generated method stub
		return dao.updDeptByEdit(dept);
	}

	@Override
	public int insDeptInfo(Hdept dept) {
		// TODO Auto-generated method stub
		return dao.insDeptInfo(dept);
	}

	@Override
	public boolean delDeptInfo(List<Integer> data) {
		// TODO Auto-generated method stub
		return dao.delDeptInfo(data)>0?true:false;
	}

}
