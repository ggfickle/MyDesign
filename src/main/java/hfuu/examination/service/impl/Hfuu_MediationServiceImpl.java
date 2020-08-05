package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hfuu.examination.dao.Hfuu_MediationDao;
import hfuu.examination.domain.Hfuu_Mediation;
import hfuu.examination.service.Hfuu_MediationService;
@Service
public class Hfuu_MediationServiceImpl implements Hfuu_MediationService {
	@Resource
	private Hfuu_MediationDao dao;
	/**
	 *  批量添加调停课信息
	 */
	@Override
	public int insMediationInfoBatch(List<Hfuu_Mediation> data) {
		return dao.insMediationInfoBatch(data);
	}
	/**
	 * 查询是否存在
	 */
	@Override
	public boolean selIsExist(Hfuu_Mediation mediation) {
		// TODO Auto-generated method stub
		return dao.selIsExist(mediation)==0?true:false;
	}

}
