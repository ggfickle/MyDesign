package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hfuu.examination.domain.UrlFilter;

public interface UrlFilterDao {
	 @Insert("insert into url_filter(name, url, roles, permissions) values(#{name},#{url},#{roles},#{permissions})")
	 public UrlFilter createUrlFilter(UrlFilter urlFilter);
	 
	 @Update("update url_filter set name=#{name},url=#{url},roles=#{roles},permissions=#{permissions} where id=#{id}")
	 public UrlFilter updateUrlFilter(UrlFilter urlFilter);
	 
	 @Select("delete from url_filter where id=?")
	 public void deleteUrlFilter(Integer urlFilterId);
	 
	 @Select("select * from url_filter where id=#{0}")
	 public UrlFilter findOne(Integer urlFilterId);
	 
	 @Select("select * from url_filter")
	 public List<UrlFilter> findAll();
}
