package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.Hfuu_Mediation;

/**
 * 调停课数据层
 */
@Component
public interface Hfuu_MediationDao {
	/**
	 * 
	 * @Title: insMediationInfoBatch
	 * @Description: 批量插入调停课记录
	 * @param data
	 * @return
	 * @author llm
	 * @date 2020-03-25 02:30:04
	 */
	int insMediationInfoBatch(List<Hfuu_Mediation> data);
	/**
	 * 
	 * @Title: SelIsExist
	 * @Description: 是否存在该调停课记录
	 * @param mediation
	 * @return
	 * @author llm
	 * @date 2020-03-25 02:29:23
	 */
	@Select("select count(id) from hfuu_mediation where tid=#{tid} and ntid=#{ntid} and pastplace=#{pastPlace} and newplace=#{newPlace} and pastdate=#{pastDate} and newdate=#{newDate}")
	int selIsExist(Hfuu_Mediation mediation);
	/**
	 * 
	 * @Title: selMediationInfoById
	 * @Description: 查询调停课信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-12 02:18:06
	 */
	@Select("select * from hfuu_mediation where id=#{0}")
	Hfuu_Mediation selMediationInfoById(String id);
	/**
	 * 
	 * @Title: selMediationInfoByTid
	 * @Description:查询是否有调停信息
	 * @param tid
	 * @return
	 * @author author
	 * @date 2020-05-12 10:36:08
	 */
	@Select("select * from hfuu_mediation where tid=#{0}")
	List<Hfuu_Mediation> selMediationInfoByTid(String tid);
	
	@Update("truncate table hfuu_mediation")
	Integer delMediation();
}
