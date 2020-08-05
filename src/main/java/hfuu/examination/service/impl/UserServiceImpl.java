package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.AdminDao;
import hfuu.examination.dao.Hfuu_TeacherDao;
import hfuu.examination.domain.User;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private AdminDao adminDao;
	@Resource
	private Hfuu_TeacherDao teacherDao;
	@Override
	public User getAdminByUserNameAndPwd(String userName, String password) {
		// TODO Auto-generated method stub
		return adminDao.selAdminByNameAndPwd(userName, password);
	}
	@Override
	public User getAdminByUserName(String userName) {
		// TODO Auto-generated method stub
		return adminDao.selAdminByName(userName);
	}
	@Override
	public User getTeacherByUserNameAndPwd(String userName, String password) {
		// TODO Auto-generated method stub
		return adminDao.selTeacherByNameAndPwd(userName, password);
	}
	@Override
	public User getTeacherByUserName(String userName) {
		
		return adminDao.selATeacherByName(userName);
	}
	@Override
	public List<Hfuu_Teacher> selTeacherInfo(String name, String college,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return teacherDao.selTeacherInfo(name, college);
	}
	@Override
	public Integer selCountTeacherInfoByLimit(String name, String college) {
		
		return teacherDao.selCountTeacherInfoByLimit(name, college);
	}
	@Override
	public boolean updTeacherPwd(String id, String pwd) {
		
		return teacherDao.updTeacherPwdById(id, pwd)>0?true:false;
	}
	@Override
	public List<User> selAllAdmin(String college,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return adminDao.selAllAdmin(college);
	}
	@Override
	public Integer selCountAllAdmin(String college) {
		
		return adminDao.selCountAllAdmin(college);
	}
	@Override
	public Integer insAdminInfo(User user) {

		return adminDao.insertExAdmin(user);
	}
	@Override
	public boolean delAdminInfo(List<Integer> data) {
		return adminDao.delAdminInfo(data)>0?true:false;
	}
	@Override
	public boolean updAdminPwd(String id, String pwd) {
		
		return adminDao.updAdminPwdById(id, pwd)>0?true:false;
	}
	@Override
	public boolean updAdminEmail(String id, String email,String smtp) {
		return adminDao.updAdminEmailById(id, email,smtp)>0?true:false;
	}
	@Override
	public User selAdminById(String id) {
		
		return adminDao.selectAdminInfoById(id);
	}
	@Override
	public boolean updAdminById(User user) {
		// TODO Auto-generated method stub
		return adminDao.adminUpdateById(user)>0?true:false;
	}
	@Override
	public boolean updTeacherEmail(String id, String email) {
		// TODO Auto-generated method stub
		return adminDao.updTeacherEmailById(id, email)>0?true:false;
	}
	@Override
	public User selTeacherById(String id) {
		// TODO Auto-generated method stub
		return adminDao.selectTeacherInfoById(id);
	}
	@Override
	public boolean updteacherById(User user) {
		// TODO Auto-generated method stub
		return adminDao.teacherUpdateById(user)>0?true:false;
	}
	
	@Override
	public User selEmailAndStmpByName(String userName) {
		return adminDao.selEmailAndStmpByName(userName);
	}

}
