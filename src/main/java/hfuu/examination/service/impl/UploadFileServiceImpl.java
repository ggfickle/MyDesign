package hfuu.examination.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.ExportFileDao;
import hfuu.examination.dao.HCalendarDao;
import hfuu.examination.dao.Hfuu_ClassRoomDao;
import hfuu.examination.dao.Hfuu_CourseDao;
import hfuu.examination.dao.Hfuu_MediationDao;
import hfuu.examination.dao.Hfuu_RebuildDao;
import hfuu.examination.dao.Hfuu_TeacherDao;
import hfuu.examination.dao.LogDao;
import hfuu.examination.dao.PublicCoursesDao;
import hfuu.examination.dao.UploadFileDao;
import hfuu.examination.domain.UploadFile;
import hfuu.examination.service.UploadFileService;

@Service
public class UploadFileServiceImpl implements UploadFileService {
	@Resource
	private UploadFileDao dao;
	@Resource
	private Hfuu_TeacherDao teacherDao;
	@Resource
	private HCalendarDao calendarDao;
	@Resource
	private ExportFileDao exportFileDao;
	@Resource
	private Hfuu_ClassRoomDao roomDao;
	@Resource
	private LogDao logDao;
	@Resource
	private Hfuu_MediationDao mediationDao;
	@Resource
	private PublicCoursesDao publicDao;
	@Resource
	private Hfuu_CourseDao courseDao;
	@Resource
	private Hfuu_RebuildDao rebuildDao;
	@Override
	public List<UploadFile> selUploadFileInfoLimit(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return dao.selUploadFilesListLimit();
	}

	@Override
	public Integer selCountUploadFile() {
		
		return dao.selCountUploadFilesList();
	}

	@Override
	public Integer insertUploadFile(UploadFile file) {
		
		return dao.insertUploadFile(file);
	}

	@Override
	public UploadFile selFileInfoById(String id) {
		// TODO Auto-generated method stub
		return dao.selFileById(id);
	}

	@Override
	public Integer updFileType(String name) {
		// TODO Auto-generated method stub
		return dao.updFileType(name);
	}

	@Override
	public Integer delUploadFile(String id) {
		// TODO Auto-generated method stub
		return dao.delUploadFileById(id);
	}

	@Override
	public Integer delUploadFileByPath(String path) {
		return dao.delUploadFileByPath(path);
	}

	@Override
	public Integer delAllFile() {
		calendarDao.delHcalendar();
		courseDao.delCourseDao();
		dao.delUploadFile();
		teacherDao.delTeacherInfo();
		roomDao.delClassRoom();
		mediationDao.delMediation();
		exportFileDao.delExportFile();
		logDao.delAllLogInfo();
		rebuildDao.delRebuild();
		publicDao.delPublicCourse();
		exportFileDao.delExportFile();
		return 1;
	}

}
