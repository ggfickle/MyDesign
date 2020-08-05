package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.HCollege;
/**
 * 
 * @ClassName: HCollegeDao 
 * @Description: 学院数据层接口
 * @author llm
 * @date 2020-03-28 03:35:28
 */
@Component
public interface HCollegeDao {
	/**
	 * 
	 * @Title: selCollegeInfo
	 * @Description: 查询学院信息
	 * @return
	 * @author llm
	 * @date 2020-03-28 03:36:55
	 */
	List<HCollege> selCollegeInfo(@Param("name")String name);
	/**
	 * 
	 * @Title: selCollegeInfoIsExist
	 * @Description: 查询院系是否存在
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-03-28 07:45:42
	 */
	@Select("select count(id) from hcollege where name=#{name}")
	int selCollegeInfoIsExist(HCollege college);
	/**
	 * 
	 * @Title: selCountOfCollege
	 * @Description: 查询总条数
	 * @return
	 * @author author
	 * @date 2020-03-29 09:30:23
	 */
	Integer selCountOfCollege(@Param("name")String name);
	/**
	 * 
	 * @Title: selById
	 * @Description: 通过id筛选院系
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-03-29 03:12:58
	 */
	@Select("select * from hcollege where id=#{0}")
	HCollege selById(int id);
	/**
	 * 
	 * @Title: selColleegeName
	 * @Description: 根据id获取院系名
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-16 11:12:12
	 */
	@Select("select name from hcollege where id=#{id}")
	String selColleegeName(@Param("id")String id);
	/**
	 * 
	 * @Title: selByDid
	 * @Description: 通过系别id筛选出院系信息
	 * @param did
	 * @return
	 * @author llm
	 * @date 2020-04-16 03:43:58
	 */
	@Select("select * from hcollege where id=(select cid from hdept where id=#{0})")
	HCollege selByDid(int did);
	/**
	 * 
	 * @Title: updCollegeInfo
	 * @Description: 修改院信息
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-03-28 07:49:25
	 */
	int updCollegeInfo(HCollege college);
	/**
	 * 
	 * @Title: insCollegeInfo
	 * @Description: 新增院信息
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-03-28 07:56:25
	 */
	@Insert("insert into hcollege values(default,#{name},#{email}) ON DUPLICATE KEY UPDATE id=id")
	int insCollegeInfo(HCollege college);
	/**
	 * 
	 * @Title: delCollegeInfoBatch
	 * @Description: 批量删除数据
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-03-28 09:06:46
	 */
	int delCollegeInfoBatch(List<Integer> data);
	/**
	 * 
	 * @Title: selIdByName
	 * @Description: 通过名称查询id
	 * @param name
	 * @return
	 * @author author
	 * @date 2020-04-03 04:33:23
	 */
	@Select("select id from hcollege where name=#{0}")
	int selIdByName(String name);
	
	/**
	 * 清空数据库
	 * @return
	 */
	@Update("truncate table hcollege")
	int delHcollege();
}
