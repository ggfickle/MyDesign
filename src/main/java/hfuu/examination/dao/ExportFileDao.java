package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hfuu.examination.domain.ExportFile;

public interface ExportFileDao {
	/**
	 * 
	 * @Title: selExportFilesListLimit
	 * @Description: 查询文件信息
	 * @return
	 * @author author
	 * @date 2020-05-16 08:33:49
	 */
	@Select("select * from export_file order by date desc")
	List<ExportFile> selExportFilesListLimit();
	
	/**
	 * 
	 * @Title: selCountExportFilesList
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-05-16 08:44:59
	 */
	@Select("select count(id) from export_file")
	Integer selCountExportFilesList();
	
	@Insert("insert into export_file values(default,#{name},#{path},#{size},#{date},0)")
	Integer insertExportFile(ExportFile file);
	/**
	 * 
	 * @Title: selFileById
	 * @Description: 通过id查询文件信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 11:04:47
	 */
	@Select("select * from export_file where id=#{0}")
	ExportFile selFileById(String id);
	/**
	 * 
	 * @Title: updAddFileNumber
	 * @Description: 增加下载次数
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 11:06:24
	 */
	@Update("update export_file set number=number+1 where id=#{id}")
	Integer updAddFileNumber(@Param("id")String id);
	/**
	 * 
	 * @Title: delExportFileById
	 * @Description: 删除文件
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-17 12:17:47
	 */
	@Delete("delete from export_file where id=#{id}")
	Integer delExportFileById(@Param("id")String id);
	/**
	 * 
	 * @Title: delExportFile
	 * @Description: 清空文件
	 * @return
	 * @author author
	 * @date 2020-05-18 12:51:21
	 */
	@Update("truncate table export_file")
	Integer delExportFile();
}
