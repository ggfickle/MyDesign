package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.Hfuu_ClassRoom;
/**
 * 
 * @ClassName: Hfuu_ClassRoomDao 
 * @Description: 教室使用信息层
 * @author llm
 * @date 2020-03-23 03:05:59
 */
@Component
public interface Hfuu_ClassRoomDao {
	/**
	 * 
	 * @Title: insertClassRoomBatch
	 * @Description:批量插入教室信息
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-03-23 03:05:44
	 */
	int insertClassRoomBatch(List<Hfuu_ClassRoom> data);
	/**
	 * 
	 * @Title: selClassRoomByNameAndDate_Addr
	 * @Description: 判断是否需要更新
	 * @param name
	 * @param date_addr
	 * @return
	 * @author author
	 * @date 2020-03-24 10:56:40
	 */
	@Select("Select count(*) from hfuu_classroom where name=#{arg0} and date_addr like concat('%',#{arg1},'%')")
	int selClassRoomByNameAndDate_Addr(String name,String date_addr);
	
	/**
	 * 
	 * @Title: updMediationToClassRoom
	 * @Description: 为教室添加调停课记录
	 * @return
	 * @author author
	 * @date 2020-03-25 04:02:26
	 */
	int updMediationToClassRoom();
	/**
	 * 
	 * @Title: selRoomInfoLimit
	 * @Description: 查询教室信息
	 * @return
	 * @author author
	 * @date 2020-04-18 08:31:34
	 */
	List<Hfuu_ClassRoom> selRoomInfoLimit(@Param("name")String name);
	/**
	 * 
	 * @Title: selCountRoom
	 * @Description: 查询教室数量
	 * @return
	 * @author author
	 * @date 2020-04-18 08:32:42
	 */
	int selCountRoom(@Param("name")String name);
	
	 /**
     * 
     * @Title: selDateAddrByName
     * @Description: TODO(描述)
     * @param place
     * @return
     * @author author
     * @date 2020-04-19 02:35:03
     */
    @Select("select * from hfuu_classroom where name=#{0}")
    Hfuu_ClassRoom selRoomInfoByName(String place);
    /**
     * 
     * @Title: updRoomDateByName
     * @Description: 教室插入监考时间
     * @param name
     * @param time
     * @return
     * @author author
     * @date 2020-05-15 03:07:16
     */
    @Update("update hfuu_classroom set date_addr=concat(date_addr,';',#{time}) where name=#{name}")
    Integer updRoomDateByName(@Param("name")String name,@Param("time")String time);
    /**
     * 
     * @Title: updAllRoomDateByName
     * @Description: 将监考安排时间移除
     * @param name
     * @param time
     * @return
     * @author author
     * @date 2020-05-15 06:15:25
     */
    @Update("update hfuu_classroom set date_addr=#{time} where name=#{name}")
    Integer updAllRoomDateByName(@Param("name")String name,@Param("time")String time);
    
    @Update("truncate table hfuu_classroom")
    Integer delClassRoom();
}
