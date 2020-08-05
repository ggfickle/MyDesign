package hfuu.examination.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hfuu.examination.dao.WxLoginDao;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.WxLoginService;

@Service
public class WxLoginServiceImpl implements WxLoginService{
	
	@Resource
	private WxLoginDao wxLoginDao;
	
	@Override
	public Hfuu_Teacher selTeacherByNameAndPwd(String username, String pwd) {
		return wxLoginDao.selTeacherByNameAndPwd(username, pwd);
	}

}
