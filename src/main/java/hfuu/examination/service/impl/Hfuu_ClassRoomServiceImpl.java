package hfuu.examination.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.Hfuu_ClassRoomDao;
import hfuu.examination.domain.Hfuu_ClassRoom;
import hfuu.examination.service.Hfuu_ClassRoomService;

@Service
public class Hfuu_ClassRoomServiceImpl implements Hfuu_ClassRoomService{
	@Autowired
	private Hfuu_ClassRoomDao dao;

	@Override
	public int insertClassRoomBatch(List<Hfuu_ClassRoom> data) {
		// TODO Auto-generated method stub
		int index=dao.insertClassRoomBatch(data);
		return index;
	}

	@Override
	public boolean selClassRoomByNameAndDate_Addr(String name, String date_addr) {
		return dao.selClassRoomByNameAndDate_Addr(name, date_addr)==0?true:false;
	}

	@Override
	public int updMediationToClassRoom() {
		// TODO Auto-generated method stub
		return dao.updMediationToClassRoom();
	}

	@Override
	public int selCountRoom(String name) {
		// TODO Auto-generated method stub
		return dao.selCountRoom(name);
	}

	@Override
	public List<Hfuu_ClassRoom> selRoomInfoLimit(String name,Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return dao.selRoomInfoLimit(name);
	}

	@Override
	public Integer updRoomDateAndAddrInfo(String name, String time) {
		// TODO Auto-generated method stub
		return dao.updRoomDateByName(name, time);
	}

}
