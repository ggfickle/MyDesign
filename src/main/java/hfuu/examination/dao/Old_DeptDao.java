package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.Old_Dept;
@Component
public interface Old_DeptDao {
	/**
	 * 
	 * @Title: selDeptInfoByLimit
	 * @Description: 分页查询系部信息
	 * @return
	 * @author author
	 * @date 2020-03-29 03:14:17
	 */
	List<Old_Dept> selDeptInfoByLimit(@Param("dept")String dept,@Param("college")String college);
	/**
	 * 
	 * @Title: selCountDeptInfoByLimit
	 * @Description: 分页查询专业信息的条数
	 * @return
	 * @author author
	 * @date 2020-03-29 03:25:59
	 */
	Integer selCountDeptInfoByLimit(@Param("dept")String dept,@Param("college")String college);
	/**
	 * 
	 * @Title: updDeptByEdit
	 * @Description: 更新名称和简写
	 * @param dept
	 * @return
	 * @author llm
	 * @date 2020-03-29 07:19:19
	 */
	Integer updDeptByEdit(Old_Dept dept);
	
	@Insert("insert into old_dept values(default,#{name},#{type},#{cid})")
	Integer insDeptInfo(Old_Dept dept);
	/**
	 * 
	 * @Title: delOldDeptInfo
	 * @Description: TODO(描述)
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-06 08:53:07
	 */
	Integer delOldDeptInfo(List<Integer> data);
}
