package hfuu.examination.dao;

import hfuu.examination.domain.upload.Hfuu_Rebuild;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface Hfuu_RebuildDao {
	/**
	 * 
	 * @Title: insRebuild
	 * @Description: 批量插入重修学生
	 * @param list
	 * @return
	 * @author author
	 * @date 2020-04-27 05:40:21
	 */
    int insRebuild(@Param("list")List<Hfuu_Rebuild> list);
    
    /**
     * 
     * @Title: selRebuildInfoBySid
     * @Description: 通过跟随班级查询学生地点
     * @param sid
     * @return
     * @author author
     * @date 2020-04-27 05:40:48
     */
    List<Hfuu_Rebuild>  selRebuildInfoBySid(List<String> sid);
    /**
     * 
     * @Title: selRebuildInfoByClassNameAndSname
     * @Description: 通过班级名和课程名筛选重修信息
     * @param className
     * @param sname
     * @return
     * @author author
     * @date 2020-04-27 05:46:23
     */
    @Select("select * from hfuu_rebuild where classname=#{classname} and sname=#{sname}")
    List<Hfuu_Rebuild> selRebuildInfoByClassNameAndSname(@Param("classname")String className,@Param("sname")String sname);

    @Update("truncate table hfuu_rebuild")
    Integer delRebuild();
}
