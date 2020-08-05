package hfuu.examination.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.Hfuu_TeacherService;

public class TeacherMailListener extends AnalysisEventListener<Hfuu_Teacher>{
	
	private Hfuu_TeacherService teacherService;
	
	public TeacherMailListener(Hfuu_TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Override
	public void invoke(Hfuu_Teacher data, AnalysisContext context) {
		Hfuu_Teacher info = teacherService.selTeacherInfoById(data.getId());
		if(info!=null) {
			teacherService.updTeacherEmailById(data);
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		
	}

}
