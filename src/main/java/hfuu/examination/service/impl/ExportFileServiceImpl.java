package hfuu.examination.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.ExportFileDao;
import hfuu.examination.domain.ExportFile;
import hfuu.examination.service.ExportFileService;
@Service
public class ExportFileServiceImpl implements ExportFileService {
	@Resource
	private ExportFileDao dao;
	@Override
	public List<ExportFile> selExportFileInfoLimit(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return dao.selExportFilesListLimit();
	}
	@Override
	public Integer selCountExportFile() {
		
		return dao.selCountExportFilesList();
	}
	@Override
	public Integer insertExportFile(ExportFile file) {
		
		return dao.insertExportFile(file);
	}
	@Override
	public ExportFile selFileInfoById(String id) {
	
		return dao.selFileById(id);
	}
	@Override
	public Integer updAddFileNumber(String id) {
		// TODO Auto-generated method stub
		return dao.updAddFileNumber(id);
	}
	@Override
	public Integer delExportFile(String id) {
		String path=selFileInfoById(id).getPath();
		new File(path).delete();
		return dao.delExportFileById(id);
	}

}
