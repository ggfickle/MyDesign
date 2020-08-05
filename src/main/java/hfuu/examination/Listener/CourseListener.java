package hfuu.examination.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hfuu.examination.domain.upload.Hfuu_Course;
import hfuu.examination.service.Hfuu_CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CourseListener extends AnalysisEventListener<Hfuu_Course> {

	private static final Logger LOGGER = LoggerFactory.getLogger(Hfuu_Course.class);

	private Hfuu_CourseService hfuu_courseService;
	private static final int BATCH_COUNT = 5;
	public int index = 0;
	List<Hfuu_Course> datas = new ArrayList<Hfuu_Course>();

	public CourseListener(Hfuu_CourseService hfuu_courseService) {
		this.hfuu_courseService = hfuu_courseService;
	}

	@Override
	public void invoke(Hfuu_Course data, AnalysisContext context) {
		List<Hfuu_Course> hfuu_courses = hfuu_courseService.selCourseCodeAndAttre(data);
		
		if (!hfuu_courses.isEmpty()) {
			for (Hfuu_Course course : hfuu_courses) {
				String sattriName = data.getSattriName();
				if (sattriName.contains("专业") && (data.getMethod() == null || data.getMethod().trim().equals("考试"))) {
					course.setSattri(0);
				} else if (sattriName.contains("公共")
						&& (data.getMethod() == null || data.getMethod().trim().equals("考试"))) {
					course.setSattri(1);
				}else {
					course.setSattri(3);
				}
				System.out.println(data.getSattri());
				if (!datas.contains(course)) {
					datas.add(course);

				}
			}
			hfuu_courses.clear();
		}
		if (datas.size() >= BATCH_COUNT) {
			saveDate();
			datas.clear();
			index++;
		}

	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		if (datas.size() > 0) {
			saveDate();
			datas.clear();
		}
		System.out.println(index);
	}

	private void saveDate() {
		for(Hfuu_Course course:datas) {
		hfuu_courseService.updCourseCodeAndAtre(course);
		}
	}

}
