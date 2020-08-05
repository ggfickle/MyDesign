package hfuu.examination.service;

import java.util.List;
import hfuu.examination.domain.Menu;

public interface MenuService {
	List<Menu> selectMenuByRoles(int pid,int rid);
	
	String selectMainTemp(int rid);
}
