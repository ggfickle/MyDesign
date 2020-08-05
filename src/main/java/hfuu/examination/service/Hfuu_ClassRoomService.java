package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.Hfuu_ClassRoom;

public interface Hfuu_ClassRoomService {
	
	int insertClassRoomBatch(List<Hfuu_ClassRoom> data);
	
	boolean selClassRoomByNameAndDate_Addr(String name,String date_addr);
	
	int updMediationToClassRoom();
	/**
	 * 
	 * @Title: selCountRoom
	 * @Description: 查询教室数量
	 * @return
	 * @author author
	 * @date 2020-04-18 08:34:39
	 */
	int selCountRoom(String name);
	/**
	 * 
	 * @Title: selRoomInfoLimit
	 * @Description: 分页查询教室信息
	 * @param name
	 * @return
	 * @author author
	 * @date 2020-04-18 08:35:31
	 */
	List<Hfuu_ClassRoom> selRoomInfoLimit(String name,Integer pageNum, Integer pageSize);
    /**
     * 
     * @Title: updRoomDateAndAddrInfo
     * @Description:更新操作
     * @param name
     * @param time
     * @return
     * @author author
     * @date 2020-05-15 03:09:14
     */
	Integer updRoomDateAndAddrInfo(String name,String time);
}
