package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.Hfuu_Mediation;

public interface Hfuu_MediationService {
	int insMediationInfoBatch(List<Hfuu_Mediation> data);
	
	boolean selIsExist(Hfuu_Mediation mediation);
}
