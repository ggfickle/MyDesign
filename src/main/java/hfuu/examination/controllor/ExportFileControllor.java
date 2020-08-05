package hfuu.examination.controllor;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.domain.ExportExcel;
import hfuu.examination.domain.ExportFile;
import hfuu.examination.domain.User;
import hfuu.examination.domain.WebResult;
import hfuu.examination.service.ExportFileService;
import hfuu.examination.service.HCollegeService;
import hfuu.examination.service.ProfessionalCoursesService;
import hfuu.examination.service.PublicCoursesService;

@Controller
public class ExportFileControllor {
	@Resource
	private ExportFileService service;
	@Resource
	private PublicCoursesService publicCoursesService;
	@Resource
	private HCollegeService collegeService;
	@Resource
	private ProfessionalCoursesService professionalService;
	private static final Logger log = LoggerFactory.getLogger(ExportFileControllor.class);
	@ResponseBody
    @RequestMapping("/getExportFileList")
    public String getExportFileListt(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize){
		WebResult wt = new WebResult();
		
		try {
			
			wt.setData(service.selExportFileInfoLimit(pageNum, pageSize));
			wt.setCount(service.selCountExportFile());
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
    }
	@ResponseBody
	@RequestMapping("/buildExportFile")
	public String buildExportFile(@RequestParam("type") String type, @RequestParam("week") String week,
			@RequestParam("courseName") String courseName, HttpServletRequest req) {
		WebResult wt = new WebResult();
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		List<ExportExcel> data = null;
		String courseAttName="";
		try {
			if (type.equals("0")) {
				data = professionalService.selProfessionalCoursesByWeekAndSname(week, courseName);
				courseAttName="专业";
			} else if (type.equals("1")) {
				data = publicCoursesService.selPublicCoursesByWeekAndSname(week, courseName);
				courseAttName="公共";
			}
			// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
			// {} 代表普通变量 {.} 代表是list的变量
			String templateFileName = req.getServletContext().getRealPath("/WEB-INF/template") + "\\"+courseAttName+"课统考安排模板.xls";
			String fileName = null;
			if (!week.equals("0") && !courseName.equals("all")) {
				fileName = req.getServletContext().getRealPath("/WEB-INF/download") + "\\第" + week + "周" + courseName
						+ "统考安排.xls";
			} else if (week.equals("0") && !courseName.equals("all")) {
				fileName = req.getServletContext().getRealPath("/WEB-INF/download") + "\\" + courseName + "统考安排.xls";
			} else if (!week.equals("0") && courseName.equals("all")) {
				fileName = req.getServletContext().getRealPath("/WEB-INF/download") + "\\第" + week + "周"+courseAttName+"课统考安排.xls";
			} else if (week.equals("0") && courseName.equals("all")) {
				fileName = req.getServletContext().getRealPath("/WEB-INF/download") + "\\"+courseAttName+"课统考安排.xls";
			}
			System.out.println("fileName is "+fileName);
			ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
			WriteSheet writeSheet = EasyExcel.writerSheet().build();

			excelWriter.fill(data, writeSheet);
			// 写入list之前的数据
			Map<String, Object> map = new HashMap<String, Object>();
			String college = collegeService.selCollegeName(String.valueOf(user.getCid()));
			map.put("college1", college);
			map.put("college2", college);
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			if (calendar.get(Calendar.MONTH) > 8 || calendar.get(Calendar.MONTH) < 1) {
				map.put("semester", "(2)");
				map.put("date", calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.YEAR) + 1));
			} else if (calendar.get(Calendar.MONTH) >= 2 && calendar.get(Calendar.MONTH) < 8) {
				map.put("semester", "(2)");
				map.put("date", (calendar.get(Calendar.YEAR) - 1) + "-" + calendar.get(Calendar.YEAR));
			}
			excelWriter.fill(map, writeSheet);
			// 千万别忘记关闭流
			excelWriter.finish();
			map.clear();
			data.clear();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			File file = new File(fileName);
			ExportFile dFile = new ExportFile();
			dFile.setPath(fileName);
			dFile.setDate(sf.format(new Date(file.lastModified())));
			dFile.setName(file.getName());
			dFile.setSize((double) (file.length() / 1024));
			service.insertExportFile(dFile);

			wt.setCode("0");
		} catch (Exception e) {
			log.error(courseAttName+"课文件生成失败:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt, SerializerFeature.DisableCircularReferenceDetect);
	}

	@ResponseBody
	@RequestMapping("/addFileNumber")
	public String AddFileNumber(String id) {
		WebResult wt = new WebResult();
		
		try {
			service.updAddFileNumber(id);
			wt.setCode("0");
		} catch (Exception e) {
			log.error("更新异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@RequestMapping("/download")
	public void download(String id,HttpServletResponse resp,HttpServletRequest req) throws IOException {
		
		ExportFile exfile=service.selFileInfoById(id);
		resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(exfile.getName(), "UTF-8"));
		ServletOutputStream os=resp.getOutputStream();
		File file=new java.io.File(exfile.getPath());
		os.write(FileUtils.readFileToByteArray(file));
		os.flush();
		os.close();
	}
	@ResponseBody
    @RequestMapping("/delExportFile")
    public String delExportFile(String id){
		WebResult wt = new WebResult();
		
		try {
			service.delExportFile(id);
			wt.setCode("0");
		} catch (Exception e) {
			log.error("文件删除异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
    }
	/**
	 * 
	 * @Title: courseWeek
	 * @Description: 获取已安排课程周次
	 * @param type
	 * @return
	 * @author author
	 * @date 2020-05-16 07:07:16
	 */
	@ResponseBody
	@RequestMapping("/courseWeek")
	public String courseWeek(@RequestParam("type") String type) {
		WebResult wt = new WebResult();
		try {
			if (type.equals("0")) {
				List<String> dataList = professionalService.selProfessionalCoursesWeek();
				wt.setData(dataList);
				wt.setCount(dataList.size());
			} else if (type.equals("1")) {
				List<String> dataList = publicCoursesService.selPublicCoursesWeek();
				wt.setData(dataList);
				wt.setCount(dataList.size());
			}
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 
	 * @Title: courseName
	 * @Description: 获取已安排课程周次
	 * @param type
	 * @return
	 * @author author
	 * @date 2020-05-16 07:07:16
	 */
	@ResponseBody
	@RequestMapping("/courseName")
	public String courseName(@RequestParam("type") String type, @RequestParam("week") String week) {
		WebResult wt = new WebResult();
		List<String> data = null;
		try {
			if (type.equals("0")) {
				data = professionalService.selCourseName(week);
			} else if (type.equals("1")) {
				data = publicCoursesService.selCourseName(week);
			}
			wt.setData(data);
			wt.setCount(data.size());
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt, SerializerFeature.DisableCircularReferenceDetect);
	}

}
