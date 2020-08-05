package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.LogDao;
import hfuu.examination.domain.Log;
import hfuu.examination.service.LogService;
@Service
public class LogServiceImpl implements LogService {
	@Resource
	private LogDao dao;
	@Override
	public int insert(Log log) {
		return dao.insLogInfo(log);
	}
	@Override
	public List<Log> selAll(String field,String order,Integer pageNum,Integer pageSize) {
		
		PageHelper.startPage(pageNum,pageSize);
		return dao.selAllListInfo(field, order);
	}
	@Override
	public Integer selCountAll() {
		// TODO Auto-generated method stub
		return dao.selCountListInfo();
	}
	@Override
	public boolean delLogInfo(List<Integer> data) {
		// TODO Auto-generated method stub
		return dao.delLogInfoByIds(data)>0?true:false;
	}
	@Override
	public boolean delAllLogInfo() {
		// TODO Auto-generated method stub
		return dao.delAllLogInfo()>0?true:false;
	}

}
