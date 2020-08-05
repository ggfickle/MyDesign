package hfuu.examination.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hfuu.examination.domain.upload.Hfuu_Rebuild;
import hfuu.examination.service.Hfuu_ClassService;
import hfuu.examination.service.Hfuu_CourseService;
import hfuu.examination.service.Hfuu_RebuildService;
import hfuu.examination.service.Hfuu_TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RebuildListener extends AnalysisEventListener<Hfuu_Rebuild> {


    private static final Logger LOGGER = LoggerFactory.getLogger(Hfuu_Rebuild.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    List<Hfuu_Rebuild> list = new ArrayList<Hfuu_Rebuild>();

    private Hfuu_CourseService hfuu_courseService;

    private Hfuu_ClassService hfuu_classService;

    private Hfuu_TeacherService hfuu_teacherService;

    private Hfuu_RebuildService hfuu_rebuildService;

    public RebuildListener(Hfuu_CourseService hfuu_courseService, Hfuu_ClassService hfuu_classService, Hfuu_TeacherService hfuu_teacherService, Hfuu_RebuildService hfuu_rebuildService) {
        this.hfuu_courseService = hfuu_courseService;
        this.hfuu_classService = hfuu_classService;
        this.hfuu_teacherService = hfuu_teacherService;
        this.hfuu_rebuildService = hfuu_rebuildService;
    }

    @Override
    public void invoke(Hfuu_Rebuild data, AnalysisContext context) {
        String checkFlag = data.getCheckFlag();
        if (checkFlag != null) {
            if (!checkFlag.contains("不") && !checkFlag.contains("该班人数较多") && !checkFlag.contains("停止重修")) {
                String className = data.getClassName();//所在的班级
                String f_name = data.getF_name();//跟随的班级
                className = className.replace("2+3班", "(2+3)");
                className = className.replace("1班", "(1)");
                className = className.replace("（1）班", "(1)");
                className = className.replace("2班", "(2)");
                className = className.replace("（2）班", "(2)");
                className = className.replace("3班", "(3)");
                className = className.replace("（3）班", "(3)");
                className = className.replace("计科1", "计科(1)");
                className = className.replace("软工", "软件工程");
                className = className.replace("（1）（卓越）班", "(1)");
                className = className.replace("（", "(");
                className = className.replace("）", ")");
                className = className.replace("专升本", "(专升本)");
                className = className.replace("网工", "网络工程");
                className = className.replace("信管(对口1)", "信息管理(对口1)");
                className = className.replace("信管(对口2)", "信息管理(对口2)");
                className = className.replace("信管对口班", "信管对口");
                className = className.replace("18信息管理(对口1)", "18信管(对口1)");
                className = className.replace("18信息管理(对口2)", "18信管(对口2)");
                if (!className.contains("(") && className.contains("2+3")) {
                    className = className.replace("2+3", "(2+3)");
                }
                data.setClassName(className);
                if (f_name != null) {
                    f_name = f_name.replace("2+3班", "(2+3)");
                    f_name = f_name.replace("1班", "(1)");
                    f_name = f_name.replace("（1）班", "(1)");
                    f_name = f_name.replace("2班", "(2)");
                    f_name = f_name.replace("（2）班", "(2)");
                    f_name = f_name.replace("3班", "(3)");
                    f_name = f_name.replace("（3）班", "(3)");
                    f_name = f_name.replace("计科1", "计科(1)");
                    f_name = f_name.replace("软工", "软件工程");
                    f_name = f_name.replace("（1）（卓越）班", "(1)");
                    f_name = f_name.replace("（", "(");
                    f_name = f_name.replace("）", ")");
                    f_name = f_name.replace("专升本", "(专升本)");
                    f_name = f_name.replace("网工", "网络工程");
                    f_name = f_name.replace("信管(对口1)", "信息管理(对口1)");
                    f_name = f_name.replace("信管(对口2)", "信息管理(对口2)");
                    f_name = f_name.replace("信管对口班", "信管对口");
                    f_name = f_name.replace("18信息管理(对口1)", "18信管(对口1)");
                    f_name = f_name.replace("18信息管理(对口2)", "18信管(对口2)");
                    if (!f_name.contains("(") && f_name.contains("2+3")) {
                        f_name = f_name.replace("2+3", "(2+3)");
                    }
                }
                data.setF_name(f_name);
                data.setF_id(hfuu_classService.selIdByName(f_name));
                data.setClassId(hfuu_classService.selIdByName(className));
                String courseId = data.getCourseId();
                if (courseId != null && courseId.length() != 9) {
                    courseId = "0" + courseId;
                }
                data.setCourseId(courseId);
                //System.out.println(f_name + "-----" + "++++++" + data.getF_id() + "---" + data.getCourseId());
                //data.setR_dateAddr(hfuu_courseService.selDateAddrByCidAndSid(data.getF_id(), data.getCourseId()));

                String t_name = data.getT_name();
                if (t_name != null && t_name.contains("（")) {
                    t_name = t_name.replace("（", "(");
                }
                String teacherId = null;
                StringBuilder sb = new StringBuilder();//用来存放多个教师的教师编号
                if (t_name != null && t_name.contains("/")) {
                    for (String s : data.getT_name().split("/")) {
                        String teacherIdOne = hfuu_teacherService.selTeacherIdByName(s);
                        //System.out.println(s);
                        sb.append(teacherIdOne + "/");
                    }
                    teacherId = sb.deleteCharAt(sb.length() - 1).toString();
                } else if (t_name != null && t_name.contains("(")) {
                    t_name = t_name.substring(0, t_name.indexOf("("));
                    teacherId = hfuu_teacherService.selTeacherIdByName(t_name);
                    data.setR_dateAddr(hfuu_courseService.selDateAddrByCidAndSid(data.getF_id(), data.getCourseId(), teacherId));
                } else if (t_name != null && t_name.contains("，")) {
                    for (String s : data.getT_name().split("，")) {
                        String teacherIdOne = hfuu_teacherService.selTeacherIdByName(s);
                        //System.out.println(s);
                        sb.append(teacherIdOne + "，");
                    }
                    teacherId = sb.deleteCharAt(sb.length() - 1).toString();
                    data.setR_dateAddr(hfuu_courseService.selDateAddrByCidAndSid(data.getF_id(), data.getCourseId(), teacherId.substring(0, teacherId.indexOf("，"))));
                } else {
                    teacherId = hfuu_teacherService.selTeacherIdByName(t_name);
                    data.setR_dateAddr(hfuu_courseService.selDateAddrByCidAndSid(data.getF_id(), data.getCourseId(), teacherId));
                }
                data.setT_id(teacherId);
                list.add(data);
            }
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("重修学生加载完成");
    }

    private void saveData() {
        hfuu_rebuildService.insRebuild(list);
    }

}
