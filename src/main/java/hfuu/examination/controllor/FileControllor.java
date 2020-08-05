package hfuu.examination.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.Listener.CourseListener;
import hfuu.examination.Listener.MediationListener;
import hfuu.examination.Listener.RebuildListener;
import hfuu.examination.Listener.TeacherListener;
import hfuu.examination.Listener.TeacherMailListener;
import hfuu.examination.domain.ExportFile;
import hfuu.examination.domain.UploadFile;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.HCalendar;
import hfuu.examination.domain.upload.Hfuu_Course;
import hfuu.examination.domain.upload.Hfuu_Rebuild;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.domain.upload.MediationExcel;
import hfuu.examination.service.HCalendarService;
import hfuu.examination.service.Hfuu_ClassRoomService;
import hfuu.examination.service.Hfuu_ClassService;
import hfuu.examination.service.Hfuu_CourseService;
import hfuu.examination.service.Hfuu_MediationService;
import hfuu.examination.service.Hfuu_RebuildService;
import hfuu.examination.service.Hfuu_TeacherService;
import hfuu.examination.service.ProfessionalCoursesService;
import hfuu.examination.service.PublicCoursesService;
import hfuu.examination.service.UploadFileService;
import hfuu.examination.utils.ExcelResolverUtils;
import hfuu.examination.utils.UploadFileUtil;

@Controller
public class FileControllor {
	
	@Resource
	private HCalendarService service;
	@Resource
	private Hfuu_ClassService hfuu_classService;
	@Resource
    private Hfuu_ClassRoomService hfuu_classRoomService;
	@Resource
    private Hfuu_TeacherService hfuu_teacherService;
	@Resource
    private Hfuu_CourseService hfuu_courseService;
	@Resource
	private Hfuu_MediationService hfuu_mediationService;
	@Resource
    private Hfuu_RebuildService hfuu_rebuildService;
	@Resource
	private PublicCoursesService publicCoursesService;
	@Resource
	private UploadFileService uploadFileService;
	@Resource
	private ProfessionalCoursesService professionalService;
	private static final Logger log = LoggerFactory.getLogger(ExportFileControllor.class);
	/**
	 * 文件上传模块
	 * @param file
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(MultipartFile file,HttpServletRequest req,HttpServletResponse resp) throws IOException, ParseException {
		String fileName=file.getOriginalFilename();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject res = new JSONObject();
		String pattern = "[\\u4E00-\\u9FA5]{2}[:-：][0-9]{6,7}[\\s][\\u4E00-\\u9FA5]{2,5}[0-9]{0,1}[._a-z]*";
		UploadFile uFile=new UploadFile();
		File file2=null;
		
		try {
			String path=req.getServletContext().getRealPath("/WEB-INF/upload");
			if(fileName.contains("教学日历")) {
				File dir=new File(path+"/calendar");
				if(dir.isDirectory()) {
					File[] files=dir.listFiles();
					if(files.length>0) {
						files[0].delete();
					}
				}
				file2=new File(path+"/calendar/"+fileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(),file2);
			}else if(fileName.matches(pattern)) {
				file2=new File(path+"/teacher/"+fileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(),file2);
			}else if(fileName.contains("邮箱")) {
				file2=new File(path+"/mail/"+fileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(),file2);
			}else if(fileName.contains("调停课")){
				file2=new File(path+"/mediation/"+fileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(),file2);
			}else if(fileName.contains("重补修")||fileName.contains("重修")) {
				file2=new File(path+"/rework/"+fileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(),file2);
			}else if(ExcelResolverUtils.isOtherXls(file.getInputStream(),fileName)){
				file2=new File(path+"/other/"+fileName);
				FileUtils.copyInputStreamToFile(file.getInputStream(),file2);
			}else{
				res.put("code", "2");
				res.put("message", "系统无法解析");
				return res.toJSONString();
			}
			uFile.setPath(file2.getAbsolutePath());
		    uFile.setDate(sf.format(new Date(file2.lastModified())));
		    uFile.setName(fileName);
		    uFile.setSize((double) (file2.length()/1024));
		    uploadFileService.insertUploadFile(uFile);
			res.put("code", "0");
			res.put("message", "文件上传成功");
		} catch (IOException e) {
			res.put("code", "1");
			res.put("message", "文件上传失败");
		}
		return res.toJSONString();
	}
	/**
	 * @Title: fileInit
	 * @Description: 系统文件加载
	 * @return
	 * @author llm
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 * @date 2020-03-20 03:36:34
	 */
	@ResponseBody
	@RequestMapping("/fileInit")
	public String fileInit(HttpServletRequest req) throws FileNotFoundException, ParseException {
		WebResult wt = new WebResult();
		String path=req.getServletContext().getRealPath("/WEB-INF/upload");
		try {
		File file1=new File(path+"/calendar");
		File[] files=file1.listFiles();
		for(File f:files) {
			List<HCalendar> date=ExcelResolverUtils.getCalendar(new FileInputStream(f));
			service.insertHcalendarBatch(date);
			uploadFileService.updFileType(f.getName());
		}
		File file2=new File(path+"/teacher");
		files=file2.listFiles();
		for(File f:files) {
			EasyExcel.read(f.getAbsolutePath(),new TeacherListener(hfuu_classService, hfuu_classRoomService, hfuu_teacherService, hfuu_courseService)).sheet().headRowNumber(0).doRead();
			uploadFileService.updFileType(f.getName());
		}
		File file3=new File(path+"/mediation");
		files=file3.listFiles();
		for(File f:files) {
			EasyExcel.read(f.getAbsolutePath(),MediationExcel.class,new MediationListener(hfuu_teacherService, hfuu_courseService,hfuu_mediationService, hfuu_classRoomService)).sheet().doRead();
			uploadFileService.updFileType(f.getName());
		}
		File file4=new File(path+"/other");
		files=file4.listFiles();
		for (File f : files) {
            if (f.getName().contains("学院所上课程查询")) {
                EasyExcel.read(f.getAbsolutePath(), Hfuu_Course.class, new CourseListener(hfuu_courseService)).sheet().headRowNumber(3).doRead();
                uploadFileService.updFileType(f.getName());
            }
        }
		File file5=new File(path+"/rework");
		files=file5.listFiles();
		for (File f : files) {
            EasyExcel.read(f.getAbsolutePath(), Hfuu_Rebuild.class, new RebuildListener(hfuu_courseService,hfuu_classService,hfuu_teacherService,hfuu_rebuildService)).sheet(2).headRowNumber(3).doRead();
            uploadFileService.updFileType(f.getName());
		}
		File file6=new File(path+"/mail");
		files=file6.listFiles();
		for (File f : files) {
			EasyExcel.read(f.getAbsolutePath(),Hfuu_Teacher.class,new TeacherMailListener(hfuu_teacherService)).sheet().doRead();
			uploadFileService.updFileType(f.getName());
		}
		//插入公共课
		publicCoursesService.insCourseIdBatch();
		//插入专业课
		professionalService.insCourseIdBatch();
			wt.setCode("0");
		}catch (Exception e) {
			log.error("加载异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		
			
	}
	/**
	 * 文件列表模块
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fileList")
	public String fileList(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		JSONObject res = new JSONObject();
		List<UploadFile> data=uploadFileService.selUploadFileInfoLimit(pageNum, pageSize);
		res.put("code", "0");
		res.put("msg", "");
		res.put("count",uploadFileService.selCountUploadFile());
		res.put("data", data);
		return res.toJSONString();
	} 
	/**
	  *  文件删除模块
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fileDel")
	public String fileDel(@RequestParam("path")String path) {
		boolean flag=new File(path).delete();
		uploadFileService.delUploadFileByPath(path);
		JSONObject res=new JSONObject();
		res.put("flag", flag);
		return res.toJSONString();	
	}
	/**
	 * 系统初始化
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fileClear")
	public String fileClear(HttpServletRequest req) {
		String path=req.getServletContext().getRealPath("/WEB-INF/upload");
		String path2=req.getServletContext().getRealPath("/WEB-INF/download");
		uploadFileService.delAllFile();
		File dirs=new File(path);
		UploadFileUtil.delFile(dirs);
		dirs=new File(path2);
		UploadFileUtil.delFile(dirs);
		return "系统文件初始化成功";
	}
}


