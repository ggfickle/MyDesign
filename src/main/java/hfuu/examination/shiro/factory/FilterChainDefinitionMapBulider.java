package hfuu.examination.shiro.factory;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import hfuu.examination.dao.UrlFilterDao;
import hfuu.examination.domain.UrlFilter;


public class FilterChainDefinitionMapBulider {
	@Autowired
	private UrlFilterDao dao;
	
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap< String, String> map=new LinkedHashMap<String, String>();
		List<UrlFilter> data=dao.findAll();
		for(int i=0;i<data.size();i++) {
			map.put(data.get(i).getUrl(), data.get(i).getRoles());
		}
		map.put("/**", "authc");
		return map;
	}
}
