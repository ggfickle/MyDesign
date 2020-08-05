package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.Old_Dept;

/**
 * 
 * @ClassName: Old_DeptService 
 * @Description: TODO(描述)
 * @author author
 * @date 2020-04-06 04:05:44
 */
public interface Old_DeptService {
	/**
	 * 
	 * @Title: selDeptInfoByLimit
	 * @Description: 分页筛选数据
	 * @return
	 * @author llm
	 * @date 2020-03-29 03:21:34
	 */
	List<Old_Dept> selDeptInfoByLimit(String dept,String college,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountByLimit
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-03-29 03:28:23
	 */
	Integer selCountByLimit(String dept,String college);
	/**
	 * 
	 * @Title: updDeptByEdit
	 * @Description: 更新数据
	 * @param dept
	 * @return
	 * @author author
	 * @date 2020-03-29 07:23:24
	 */
	Integer updDeptByEdit(Old_Dept dept);
	/**
	 * 
	 * @Title: insDeptInfo
	 * @Description: 新增专业信息
	 * @param dept
	 * @return
	 * @author author
	 * @date 2020-04-03 04:37:20
	 */
	int insDeptInfo(Old_Dept dept);
	/**
	 * 
	 * @Title: delOldDeptInfo
	 * @Description: 批量删除
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-06 08:55:21
	 */
	boolean delOldDeptInfo(List<Integer> data);
}
