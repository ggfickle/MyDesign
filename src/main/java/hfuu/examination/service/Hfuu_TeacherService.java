package hfuu.examination.service;

import org.apache.ibatis.annotations.Param;

import hfuu.examination.domain.upload.Hfuu_Teacher;

import java.util.List;
import java.util.Map;

public interface Hfuu_TeacherService {

    int selCid(String name);

    int insTeacherInfo(@Param("list") List<Hfuu_Teacher> list);

    List<Hfuu_Teacher> selIsTeacher(Hfuu_Teacher hfuu_teacher);
    
    String selTid(String name,String sname);
    
    String selTeacherIdByName(String name);
    /**
     * 
     * @Title: updAddChiefById
     * @Description: 增加主监考次数
     * @return
     * @author author
     * @date 2020-05-15 02:57:44
     */
    Integer updAddChiefById(String id);
    /**
     * 
     * @Title: updAddDeputyById
     * @Description: 增加副监考次数
     * @return
     * @author author
     * @date 2020-05-15 02:58:12
     */
    Integer updAddDeputyById(String id);
    
    /**
     * 生成考务信息在邮件通知显示
    * @Title: getArrangementInfo
    * @Description: 
    * @param @return    参数
    * @return List<Hfuu_Teacher>    返回类型
    * @throws
     */
    List<Hfuu_Teacher> getArrangementInfo();
    
    List<Hfuu_Teacher> getArrangementInfoLimit(Integer pageNum, Integer pageSize);
    /**
     * 
     * @Title: selTeacherInfoById
     * @Description: 查询教师信息
     * @param id
     * @return
     * @author author
     * @date 2020-04-17 08:06:37
     */
    Hfuu_Teacher selTeacherInfoById(String id);
    /**
     * 根据教师id更新教师的邮箱
    * @Title: updTeacherEmailById
    * @Description: 
    * @param @param teacher
    * @param @return    参数
    * @return Integer    返回类型
    * @throws
     */
    Integer updTeacherEmailById(Hfuu_Teacher teacher);
    
    /**
     * 查询教师条数
    * @Title: selTeacherCount
    * @Description: 
    * @param @return    参数
    * @return Integer    返回类型
    * @throws
     */
    Integer selTeacherCount();
    /**
     * 
     * @Title: selFrequency
     * @Description:查询监考科目数
     * @param id
     * @return
     * @author author
     * @date 2020-05-27 03:09:16
     */
    List<Map<String, Integer>> selFrequency(String id);
}
