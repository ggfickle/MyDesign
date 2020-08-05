package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.UploadFile;

/**
 * 
 * @ClassName: UploadFileService 
 * @Description: 导入文件信息服务接口
 * @author author
 * @date 2020-05-16 08:35:08
 */

public interface UploadFileService {
	/**
	 * 
	 * @Title: selUploadFileInfoLimit
	 * @Description: 导出文件
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author author
	 * @date 2020-05-16 08:35:04
	 */
	List<UploadFile> selUploadFileInfoLimit(Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title:  selCountUploadFile
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-05-16 08:45:40
	 */
	Integer selCountUploadFile();
	/**
	 * 
	 * @Title: insertUploadFile
	 * @Description: 插入文件信息
	 * @param file
	 * @return
	 * @author author
	 * @date 2020-05-17 10:44:17
	 */
	Integer insertUploadFile(UploadFile file);
	
	/**
	 * 
	 * @Title: selFileInfoById
	 * @Description: 通过id选择文件信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 11:07:33
	 */
	UploadFile selFileInfoById(String id);
	/**
	 * 
	 * @Title: updFileType
	 * @Description: 
	 * @param name
	 * @return
	 * @author author
	 * @date 2020-05-17 11:07:42
	 */
	Integer updFileType(String name);
	/**
	 * 
	 * @Title: delUploadFile
	 * @Description: 删除文件
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 12:16:15
	 */
	Integer delUploadFile(String id);
	/**
	 * 
	 * @Title: delUploadFileByPath
	 * @Description: 通过路径删除信息
	 * @param path
	 * @return
	 * @author author
	 * @date 2020-05-18 01:09:10
	 */
	Integer delUploadFileByPath(String path);
	/**
	 * 
	 * @Title: delAllFile
	 * @Description: 清空文件
	 * @return
	 * @author author
	 * @date 2020-05-18 01:10:51
	 */
	Integer delAllFile();
}
