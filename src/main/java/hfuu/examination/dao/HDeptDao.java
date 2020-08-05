package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.Hdept;
@Component
public interface HDeptDao {
	/**
	 * 
	 * @Title: selDeptInfoByLimit
	 * @Description: 分页查询专业信息
	 * @return
	 * @author author
	 * @date 2020-03-29 03:14:17
	 */
	List<Hdept> selDeptInfoByLimit(@Param("major")String major,@Param("college")String college);
	/**
	 * 
	 * @Title: selCountDeptInfoByLimit
	 * @Description: 分页查询专业信息的条数
	 * @return
	 * @author author
	 * @date 2020-03-29 03:25:59
	 */
	Integer selCountDeptInfoByLimit(@Param("major")String major,@Param("college")String college);
	/**
	 * 
	 * @Title: updDeptByEdit
	 * @Description: 更新名称和简写
	 * @param dept
	 * @return
	 * @author llm
	 * @date 2020-03-29 07:19:19
	 */
	Integer updDeptByEdit(Hdept dept);
	
	@Insert("insert into hdept values(default,#{name},#{sname},#{cid})")
	Integer insDeptInfo(Hdept dept);
	/**
	 * 
	 * @Title: delDeptInfo
	 * @Description: 批量删除
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-06 08:50:56
	 */
	Integer delDeptInfo(List<Integer> data);
}
