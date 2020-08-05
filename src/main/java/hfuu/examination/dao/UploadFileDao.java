package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hfuu.examination.domain.UploadFile;


public interface UploadFileDao {
	/**
	 * 
	 * @Title: selUploadFilesListLimit
	 * @Description: 查询文件信息
	 * @return
	 * @author author
	 * @date 2020-05-16 08:33:49
	 */
	@Select("select * from upload_file order by type")
	List<UploadFile> selUploadFilesListLimit();
	
	/**
	 * 
	 * @Title: selCountUploadFilesList
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-05-16 08:44:59
	 */
	@Select("select count(id) from upload_file")
	Integer selCountUploadFilesList();
	
	@Insert("insert into upload_file values(default,#{name},#{path},#{size},#{date},0)")
	Integer insertUploadFile(UploadFile file);
	/**
	 * 
	 * @Title: selFileById
	 * @Description: 通过id查询文件信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 11:04:47
	 */
	@Select("select * from upload_file where id=#{0}")
	UploadFile selFileById(String id);
	/**
	 * 
	 * @Title: updFileType
	 * @Description: 修改类型码
	 * @param name
	 * @return
	 * @author author
	 * @date 2020-05-17 11:06:24
	 */
	@Update("update upload_file set type=1 where name=#{name}")
	Integer updFileType(@Param("name")String name);
	/**
	 * 
	 * @Title: delExportFileById
	 * @Description: 删除文件
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 12:17:47
	 */
	@Delete("delete from upload_file where id=#{id}")
	Integer delUploadFileById(@Param("id")String id);
	
	@Delete("delete from upload_file where path=#{path}")
	Integer delUploadFileByPath(@Param("path")String path);
	 @Update("truncate table upload_file")
	Integer delUploadFile();
	
}
