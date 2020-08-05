package hfuu.examination.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.upload.Hfuu_Class;

import java.util.List;
import java.util.Map;
@Component
public interface Hfuu_ClassDao {

    /**
     * 从专业信息表中查找对应专业的编号
     * @param sname
     * @return
     */
    @Select("select id from hdept where name=#{0}")
    Integer selDid(String name);
    /**
     * 
     * @Title: selIdLikeName
     * @Description: 模糊查询
     * @param name
     * @return
     * @author llm
     * @date 2020-03-23 09:19:59
     */
    @Select("select DISTINCT id,sname from hdept where sname like concat('%',#{0},'%')")
    List<Map<String, Object>> selDIdLikeName(String name);
    /**
     *从班级信息表中查找有没有已经存在的班级信息
     * @param hfuu_class
     * @return
     */
    @Select("select name,did from hfuu_class where name=#{name} and did=#{did}")
    List<Hfuu_Class> selByName(Hfuu_Class hfuu_class);

    /**
     * 将新的班级信息存到数据库中
     * @param hfuu_class
     * @return
     */
    @Insert("insert into hfuu_class values(default,#{name},#{did})")
    int insClassInfo(Hfuu_Class hfuu_class);
    /**
     *  
     * @Title: insClassInfoBatch
     * @Description: 批量插入班级信息
     * @param data
     * @return
     * @author llm
     * @date 2020-03-23 04:04:04
     */
    int insClassInfoBatch(List<Hfuu_Class> data);
    /**
     * 从班级信息表中查找对应的班级编号
     * @param name
     * @return
     */
    @Select("select id from hfuu_class where name =#{0}")
    Integer selIdByName(String name);
    /**
     * 
     * @Title: selClassInfoById
     * @Description: TODO(描述)
     * @param id
     * @return
     * @author author
     * @date 2020-04-19 02:32:51
     */
    Hfuu_Class selClassInfoById(String id);
   
    
}
