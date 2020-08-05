package hfuu.examination.service;

import hfuu.examination.domain.upload.Hfuu_Rebuild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Hfuu_RebuildService {

    int insRebuild(@Param("list") List<Hfuu_Rebuild> list);
    
    List<Hfuu_Rebuild> selRebuildInfoByClassNameAndSname(String classname,String sname);
    
    /**
	 * 
	 * @Title: selCountRebuild
	 * @Description: 查询重修人数
	 * @param sid
	 * @param place
	 * @param week
	 * @param whichDay
	 * @param time
	 * @return
	 * @author author
	 * @date 2020-04-19 08:17:18
	 */
	List<Hfuu_Rebuild> selCountRebuild(List<String> sid);
}
