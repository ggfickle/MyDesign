package hfuu.examination.service;

import java.util.List;

import org.springframework.stereotype.Component;

import hfuu.examination.domain.UrlFilter;
@Component
public interface UrlFilterService {  
    public UrlFilter createUrlFilter(UrlFilter urlFilter);  
    
    public UrlFilter updateUrlFilter(UrlFilter urlFilter);  
   
    public void deleteUrlFilter(int urlFilterId);  
    
    public UrlFilter findOne(int urlFilterId);  
   
    public List<UrlFilter> findAll();  
}  
