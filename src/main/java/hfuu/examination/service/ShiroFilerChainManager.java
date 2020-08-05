package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.UrlFilter;

public interface ShiroFilerChainManager {
	void init();
	void initFilterChains(List<UrlFilter> urlFilters);
}
