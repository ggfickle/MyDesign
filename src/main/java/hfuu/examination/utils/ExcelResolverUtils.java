package hfuu.examination.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.excel.EasyExcel;

import hfuu.examination.domain.upload.HCalendar;

/**
 * 校历解析工具类
 * @author Administrator
 *
 */
public class ExcelResolverUtils {
	private final static String[] NUMBER= {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
	private final static String[] CHARS= {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二一","二二","二三","二四","二五","二六","二七","二八","二九 ","三零"};
	private static Pattern pattern = Pattern.compile("[0-9]*");
	/**
	 *     为校历类赋值
	 * @param is
	 * @return
	 * @throws ParseException
	 */
	public static List<HCalendar> getCalendar(InputStream is) throws ParseException {
		  List<Map<String, String>> list=new  ExcelResolverUtils().readCalendar(is);
		  List<HCalendar> data=new ArrayList<HCalendar>();
		  Map<String,String> year=list.get(0);
		  Map<String,String> month=list.get(1);
		  Map<String,String> day=list.get(2);
		  Map<String,String> week=list.get(3);
		  for(int i=1;i<week.size()&&Integer.valueOf(week.get(""+i))>(week.get(""+(i-1)).equals("周次")?0:Integer.valueOf(week.get(""+(i-1))));i++) {
			  boolean afterFlag=true;
			  boolean beforeflag=true;
			  HCalendar hc=new HCalendar();
			  String date=null;
			  hc.setWeek(Integer.valueOf(week.get(""+i)));
			  if(!isNumber(day.get(""+i))) {
				  SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				  GregorianCalendar calendar=new GregorianCalendar();
				  calendar.setTime(df.parse(data.get(data.size()-1).getDate()));
				  calendar.add(Calendar.DAY_OF_MONTH, 7);
				  day.replace(""+i, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
			  }
			  if(i>=year.size()) {
				  date=year.get(""+(year.size()-1));
			  }else {
				  date=year.get(""+i);
			  }
			  if(i<month.size()-1&&i>1) {
				  afterFlag=Integer.valueOf(month.get(""+i)).equals(Integer.valueOf(month.get(""+(i+1))));
				 
			  }  
			  if(i<month.size()&&i>1) { 
			  beforeflag=Integer.valueOf(month.get(""+i)).equals(Integer.valueOf(month.get(""+(i-1))));
			  }
			  if((!afterFlag)&&beforeflag&&i>1&&(Integer.valueOf(day.get(""+i))<Integer.valueOf(day.get(""+(i-1))))){
				  date=date+"-"+(Integer.valueOf(month.get(""+i))+1==13?1:Integer.valueOf(month.get(""+i))+1);
			  }else if((!beforeflag)&&afterFlag&&i>1&&(Integer.valueOf(day.get(""+i))>Integer.valueOf(day.get(""+(i-1))))&&(Integer.valueOf(day.get(""+i))>Integer.valueOf(day.get(""+(i+1))))) {
				  date=date+"-"+(Integer.valueOf(month.get(""+i))-1==0?12:Integer.valueOf(month.get(""+i))-1);
			  }else{
				  if(i>=month.size()) {
					  date=date+"-"+month.get(""+(month.size()-1));
				  }else {
					  date=date+"-"+month.get(""+i);
				  }
			  }  
			  date=date+"-"+day.get(""+i);
			  hc.setDate(date);
			  data.add(hc);
		  }
		return data;
	}
	/**
	 * 从校历中筛选所需要内容
	 * @param is
	 * @return
	 */
	private List<Map<String, String>> readCalendar(InputStream is){
		List<Object> list=EasyExcel.read(is).sheet(0).doReadSync();
	    List<Map<String, String>> convertList=new ArrayList<>();
	    GregorianCalendar cal=new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis());
		int month=cal.get(Calendar.MONTH)+1;
		boolean startFlag=true;//开始对字符串进行操作
		boolean endFlag=true;//操作结束
		String empty=null;//用来对null进行填充
		for(int i=0;i<list.size()-1;i++) {
			HashMap<String, String> map=new HashMap<String, String>();
			StringBuilder strs=new StringBuilder(list.get(i).toString());
			strs.deleteCharAt(0).deleteCharAt(strs.length()-1);
			if(month>=3&&month<8&&startFlag==true) {
				if(!strs.toString().contains("第二学期")) {
					continue;
				}else {
					startFlag=false;
				}
			}else if(month<3||month>9){
				if(strs.toString().contains("第二学期")) {
					break;
				}
			}
			
			if(endFlag==false) {
				break;
			}else if(strs.toString().contains("周次")) {
				endFlag=false;
			}else if(strs.toString().contains("学期")
					||strs.toString().contains("星期二")
						||strs.toString().contains("星期三")
							||strs.toString().contains("星期四")
								||strs.toString().contains("星期五")
									||strs.toString().contains("星期六")
										||strs.toString().contains("星期日")) {
				continue;
			}
			for(String str:strs.toString().split(",")) {			
				
				String[] s=str.split("=");
				
				if(s[1].contains(".0")) {
					s[1]=s[1].substring(0, s[1].length()-2);
				}
				s[1]=covertNumber(s[1]).trim();
				if (!s[1].equals("null")) {
					empty=s[1];
				}else{
					s[1]=empty;
				}
				map.put(s[0].trim(), s[1]);
			}
			convertList.add(map);
		}
		return convertList;
	}
	/**
	 * 汉字数字转换成数字
	 * @param s
	 * @return
	 */
	private  String covertNumber(String s) {
		int i=0;
		for(String str:CHARS) {
			if(str.equals(s)) {
				break;
			}
			if(i==29) {
				return s;
			}
			i++;
		}
		return NUMBER[i];
	}
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		
		
	    return pattern.matcher(str).matches();  
	}
	/**
	 * 判断是否是存在数字
	 * @param str
	 * @return
	 */
	public static boolean isExistNumber(String str,String dist) {
		
		for(String s:str.split(",")) {
			if(s.equals(dist)) {
				return true;
			}
		}
	    return false;  
	}
	public static List<String> getRemainTime(List<String> list,String strs,String whichDay,String week){
		//判断课程是否为设计课程
		if(!strs.contains(":")) {
				if(isExistNumber(strs,week)) {
					return new ArrayList<String>();
				}
				return list;
		}
		for(String str:strs.split(";")) {
			String[] spit=str.split(":");
			if(spit[1].equals(whichDay)) {
				if(isExistNumber(spit[0],week)) {
					for(int i=0;i<list.size();i++) {
						if(list.get(i).contains(spit[2])) {
							list.remove(i);
						}
					}
				}
			}
			
		}
		return list;
	}
	/**
	 * 判断文件是否能被系统解析
	 * @param is
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static boolean isOtherXls(InputStream is,String fileName) throws IOException {
		int index=fileName.indexOf(".");
		if(fileName.substring(index).equals(".xls")) {
			return readXLS(is);
		}else if(fileName.substring(index).equals(".xlsx")){
			return readXLSX(is);
		}else {
			return false;
		}
	}
	/**
	 * 读取第一行判断该文件系统能否解析
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static boolean readXLS(InputStream is) throws IOException {
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			workbook.getActiveSheetIndex();
			HSSFSheet sheet=workbook.getSheetAt(0);
			HSSFRow row=sheet.getRow(2);
			StringBuffer sb=new StringBuffer();
			boolean flag=false;
			for(Iterator<Cell> iterable=row.iterator();iterable.hasNext();) {
				HSSFCell cell=(HSSFCell) iterable.next();
				sb.append(cell.getStringCellValue().trim());
			}
			String str=sb.toString();
			if(str.contains("课程代码")&&str.contains("课程名称")&&str.contains("教师职工号")&&str.contains("教师姓名")) {
				flag=true;
			}
			workbook.close();
			return flag;
	}
	public static boolean readXLSX(InputStream is) throws IOException {
		XSSFWorkbook workbook=new XSSFWorkbook(is);
		XSSFSheet sheet=workbook.getSheetAt(0);
		XSSFRow row=sheet.getRow(2);
		StringBuffer sb=new StringBuffer();
		boolean flag=false;
		for(Iterator<Cell> iterable=row.iterator();iterable.hasNext();) {
				XSSFCell cell=(XSSFCell) iterable.next();
				sb.append(cell.getStringCellValue().trim());
		}
		String str=sb.toString();
		if(str.contains("课程代码")&&str.contains("课程名称")&&str.contains("教师职工号")&&str.contains("教师姓名")) {
			flag=true;
		}
		workbook.close();
		return flag;
	}
}
