package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hfuu.examination.dao.MenuDao;
import hfuu.examination.domain.Menu;
import hfuu.examination.service.MenuService;
@Service
public class MenuServiceImpl implements MenuService {
	@Resource
	private MenuDao dao;
	@Override
	public List<Menu> selectMenuByRoles(int pid, int rid) {
		// TODO Auto-generated method stub
		return dao.selectMenuByRoles(pid, rid);
	}

	@Override
	public String selectMainTemp(int rid) {
		// TODO Auto-generated method stub
		return dao.selMainTemp(rid);
	}

}
