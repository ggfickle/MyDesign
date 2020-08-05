package hfuu.examination.service.impl;

import hfuu.examination.dao.Hfuu_RebuildDao;
import hfuu.examination.domain.upload.Hfuu_Rebuild;
import hfuu.examination.service.Hfuu_RebuildService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class Hfuu_RebuildServiceImpl implements Hfuu_RebuildService {

    @Resource
    private Hfuu_RebuildDao hfuu_rebuildDao;

    @Override
    public int insRebuild(List<Hfuu_Rebuild> list) {
        return hfuu_rebuildDao.insRebuild(list);
    }

	@Override
	public List<Hfuu_Rebuild> selRebuildInfoByClassNameAndSname(String className, String sname) {
		// TODO Auto-generated method stub
		return hfuu_rebuildDao.selRebuildInfoByClassNameAndSname(className, sname);
	}
	@Override
	public List<Hfuu_Rebuild> selCountRebuild(List<String> sid) {
		return hfuu_rebuildDao.selRebuildInfoBySid(sid);
	
	}
}
