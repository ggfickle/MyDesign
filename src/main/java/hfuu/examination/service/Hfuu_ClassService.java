package hfuu.examination.service;


import java.util.List;
import java.util.Map;

import hfuu.examination.domain.upload.Hfuu_Class;

public interface Hfuu_ClassService {

    int selDid(String name);

    List<Hfuu_Class> selByName(Hfuu_Class hfuu_class);

    int insClassInfo(Hfuu_Class hfuu_class);
    
    int insClassInfoBatch(List<Hfuu_Class> data);

    int selIdByName(String name);
    
    List<Map<String, Object>> selDIdByLikeName(String name);
}
