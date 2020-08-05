package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.HCollege;
/**
 * 
 * @ClassName: HCollegeService 
 * @Description: 学院服务层接口
 * @author llm
 * @date 2020-03-28 04:18:02
 */
public interface HCollegeService {
	/**
	 * 
	 * @Title: selCollegeInfo
	 * @Description: 获取学院信息
	 * @return
	 * @author author
	 * @date 2020-03-28 04:20:58
	 */
	 List<HCollege> selCollegeInfo();
	/**
	 * 
	 * @Title: selCollegeInfoLimit
	 * @Description: 分页查询
	 * @return
	 * @author author
	 * @date 2020-03-29 09:25:52
	 */
     List<HCollege> selCollegeInfoLimit(String name,Integer pageNum,Integer pageSize);
	 /**
	  * 
	  * @Title: selCollegeInfoIsExist
	  * @Description: 判断是否院系信息存在
	  * @param college
	  * @return
	  * @author author
	  * @date 2020-03-28 07:46:52
	  */
	 boolean selCollegeInfoIsExist(HCollege college);
	 /**
	  * 
	  * @Title: insCollegeInfo
	  * @Description: 新增院系信息
	  * @param college
	  * @return
	  * @author author
	  * @date 2020-03-28 08:01:19
	  */
	 boolean insCollegeInfo(HCollege college);
	 /**
	  * 
	  * @Title: delCollegeInfo
	  * @Description: 批量删除数据
	  * @param colleges
	  * @return
	  * @author author
	  * @date 2020-03-28 09:09:17
	  */
	 boolean delCollegeInfo(List<Integer> data);
	 /**
	  * 
	  * @Title: updCollegeInfo
	  * @Description: 更新学院信息
	  * @param college
	  * @return
	  * @author llm
	  * @date 2020-03-29 01:14:06
	  */
	 boolean updCollegeInfo(HCollege college);
	 /**
	  * 
	  * @Title: selCountCollegeInfo
	  * @Description: 查询数据总数量
	  * @return
	  * @author author
	  * @date 2020-03-29 09:31:22
	  */
	 Integer selCountCollegeInfo(String name);
	 /**
	  * 
	  * @Title: selIdByName
	  * @Description: 通过名称查询id
	  * @param name
	  * @return
	  * @author author
	  * @date 2020-04-03 04:34:21
	  */
	 int selIdByName(String name);
	 
	 String selCollegeName(String id);
}
