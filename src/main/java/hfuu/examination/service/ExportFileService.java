package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.ExportFile;

/**
 * 
 * @ClassName: ExportFileService 
 * @Description: 导出文件信息服务接口
 * @author author
 * @date 2020-05-16 08:35:08
 */

public interface ExportFileService {
	/**
	 * 
	 * @Title: selExportFileInfoLimit
	 * @Description: 导出文件
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author author
	 * @date 2020-05-16 08:35:04
	 */
	List<ExportFile> selExportFileInfoLimit(Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountExportFile
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-05-16 08:45:40
	 */
	Integer selCountExportFile();
	/**
	 * 
	 * @Title: insertExportFile
	 * @Description: 插入文件信息
	 * @param file
	 * @return
	 * @author author
	 * @date 2020-05-17 10:44:17
	 */
	Integer insertExportFile(ExportFile file);
	
	/**
	 * 
	 * @Title: selFileInfoById
	 * @Description: 通过id选择文件信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 11:07:33
	 */
	ExportFile selFileInfoById(String id);
	/**
	 * 
	 * @Title: updAddFileNumber
	 * @Description: 增加文件下载次数
	 * @param number
	 * @return
	 * @author author
	 * @date 2020-05-17 11:07:42
	 */
	Integer updAddFileNumber(String id);
	/**
	 * 
	 * @Title: delExportFile
	 * @Description: 删除文件
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 12:16:15
	 */
	Integer delExportFile(String id);
}
