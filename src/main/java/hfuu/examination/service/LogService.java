package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.Log;
/**
 * 
 * @ClassName: LogService 
 * @Description: 日志信息
 * @author author
 * @date 2020-04-16 08:31:53
 */
public interface LogService {
	/**
	 * 
	 * @Title: insert
	 * @Description: 添加日志
	 * @param log
	 * @return
	 * @author author
	 * @date 2020-04-16 08:32:07
	 */
	int insert(Log log);
	/**
	 * 
	 * @Title: selAll
	 * @Description: 查询所有
	 * @return
	 * @author author
	 * @date 2020-05-11 09:31:54
	 */
	List<Log> selAll(String field,String order,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountAll
	 * @Description: 查询全部日志数量
	 * @return
	 * @author author
	 * @date 2020-05-11 11:40:28
	 */
	Integer selCountAll();
	/**
	 * 
	 * @Title: delLogInfo
	 * @Description: TODO(描述)
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-05-11 11:50:41
	 */
	boolean delLogInfo(List<Integer> data);
	/**
	 * 
	 * @Title: delAllLogInfo
	 * @Description: 清空日志记录
	 * @return
	 * @author author
	 * @date 2020-05-11 11:51:01
	 */
	boolean delAllLogInfo();
}
