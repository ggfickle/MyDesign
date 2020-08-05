package hfuu.examination.service.impl;


import org.springframework.stereotype.Service;

import hfuu.examination.dao.Hfuu_ClassDao;
import hfuu.examination.domain.upload.Hfuu_Class;
import hfuu.examination.service.Hfuu_ClassService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class Hfuu_ClassServiceImpl implements Hfuu_ClassService {

    @Resource
    private Hfuu_ClassDao hfuu_classMapper;

    @Override
    public int selDid(String name) {
        return hfuu_classMapper.selDid(name)==null?0:hfuu_classMapper.selDid(name);
    }

    @Override
    public List<Hfuu_Class> selByName(Hfuu_Class hfuu_class) {
        return hfuu_classMapper.selByName(hfuu_class);
    }

    @Override
    public int insClassInfo(Hfuu_Class hfuu_class) {
        return hfuu_classMapper.insClassInfo(hfuu_class);
    }

    @Override
    public int selIdByName(String name) {
        return hfuu_classMapper.selIdByName(name)==null?0:hfuu_classMapper.selIdByName(name);
    }

	@Override
	public int insClassInfoBatch(List<Hfuu_Class> data) {
		// TODO Auto-generated method stub
		return hfuu_classMapper.insClassInfoBatch(data);
	}

	@Override
	public List<Map<String, Object>> selDIdByLikeName(String name) {
		
		return hfuu_classMapper.selDIdLikeName(name);
	}


}
