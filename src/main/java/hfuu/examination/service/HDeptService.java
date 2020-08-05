package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.Hdept;

/**
 * 
 * @ClassName: HDeptService 
 * @Description: 专业service层
 * @author llm
 * @date 2020-03-29 03:20:29
 */
public interface HDeptService {
	/**
	 * 
	 * @Title: selDeptInfoByLimit
	 * @Description: 分页筛选数据
	 * @return
	 * @author llm
	 * @date 2020-03-29 03:21:34
	 */
	List<Hdept> selDeptInfoByLimit(String major,String college,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountByLimit
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-03-29 03:28:23
	 */
	Integer selCountByLimit(String major,String college);
	/**
	 * 
	 * @Title: updDeptByEdit
	 * @Description: 更新数据
	 * @param dept
	 * @return
	 * @author author
	 * @date 2020-03-29 07:23:24
	 */
	Integer updDeptByEdit(Hdept dept);
	/**
	 * 
	 * @Title: insDeptInfo
	 * @Description: 新增专业信息
	 * @param dept
	 * @return
	 * @author author
	 * @date 2020-04-03 04:37:20
	 */
	int insDeptInfo(Hdept dept);
	
	boolean delDeptInfo(List<Integer> data);
}
