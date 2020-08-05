package hfuu.examination.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hfuu.examination.domain.UploadFile;
/**
 * 已上传文档文具类
 * 查看文档
 * @author Administrator
 *
 */
public class UploadFileUtil {
	
	/**
	 * 删除文件系统
	 * @param file
	 */
	public static void delFile(File file){
		if(file.isFile()) {
			file.delete();
		}
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for(File f:files){
				delFile(f);
			}
		}
		
	}
}
