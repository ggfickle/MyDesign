package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.Log;
@Component
public interface LogDao {
	/**
	 * 
	 * @Title: insLogInfo
	 * @Description: 插入日志信息
	 * @return
	 * @author author
	 * @date 2020-04-16 08:24:12
	 */
	@Insert("Insert into t_log values(default,#{reqSource},#{type},#{ip},#{fullName},"
			+ "#{loginName},#{moduleType},#{operateCode},#{operateValue},#{operateDateTime},#{createDateTime},"
			+ "#{remark},#{operateStatus},#{localAddr},#{method},#{param},#{exceptionDetail})")
	Integer insLogInfo(Log log);
	
	@Select("select * from t_log order by ${field} ${order}")
	List<Log> selAllListInfo(@Param("field")String field,@Param("order")String order);
	
	@Select("select count(id) from t_log")
	Integer selCountListInfo();
	/**
	 * 
	 * @Title: delLogInfoByIds
	 * @Description: 删除某些日志记录
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-05-11 11:50:18
	 */
	Integer delLogInfoByIds(List<Integer> data);
	/**
	 * 
	 * @Title: delAllLogInfo
	 * @Description: 删除所有日志记录
	 * @return
	 * @author author
	 * @date 2020-05-11 11:50:05
	 */
	@Delete("truncate table t_log")
	Integer delAllLogInfo();
}
