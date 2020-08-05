package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import hfuu.examination.domain.Menu;

public interface MenuDao {
	
	List<Menu> selectMenuByRoles(@Param("pid") int pid,@Param("rid") int rid);
	
	@Select("select url from url_filter where id=(select m.uid from menu m  join role_menu r on r.mid=m.id where r.rid=#{rid} and m.pid=0)")
	String selMainTemp(@Param("rid")int rid);
}
