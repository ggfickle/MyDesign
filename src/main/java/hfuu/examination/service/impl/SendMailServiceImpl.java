package hfuu.examination.service.impl;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.sun.mail.util.MailSSLSocketFactory;

import hfuu.examination.dao.Hfuu_TeacherDao;
import hfuu.examination.domain.Arrangement;
import hfuu.examination.domain.User;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.SendMailService;

@Service
public class SendMailServiceImpl implements SendMailService{
	
	@Resource
	private Hfuu_TeacherDao teacherDao;
	
	@Override
	public String getTeacherMailInfoById(String id) {
		return teacherDao.getTeacherMailInfoById(id);
	}
	
	public String getMainArrangement(Hfuu_Teacher teacher,String apiName) {
		List<Arrangement> mainArrangementList = teacher.getMainArrangement();
		String mainArrangementInfo="";//主监考考务
		Map<String,String> map=new HashMap<>();//用于合并合班考试的班级信息
		String timePlaceName="";
		String className="";
		
		if(apiName.equals("wx")) {
			apiName="\n";
		}else {
			apiName="<br/>";
		}
		//处理主监考合班
		for(int i=0;i<mainArrangementList.size();i++) {
			className=mainArrangementList.get(i).getName();//班级名称
			if(apiName.equals("\n")) {
				String[] timeArr = mainArrangementList.get(i).getTime().split(":");
				timePlaceName=mainArrangementList.get(i).getSname()//课程名
						+"-----第"+timeArr[0]+"周-星期"+timeArr[1]+"-第"+timeArr[2]+"节----"//考试时间
						+"-"+mainArrangementList.get(i).getPlace()+" "+apiName;//考试地点
			}
			if(apiName.equals("<br/>")) {
				String timeArr=mainArrangementList.get(i).getTime();
				timePlaceName=mainArrangementList.get(i).getSname()//课程名
						+timeArr//考试时间
						+"-"+mainArrangementList.get(i).getPlace()+" "+apiName;//考试地点
			}
			if(map.isEmpty()) {
				map.put(timePlaceName, className);
			}else {
				if(map.containsKey(timePlaceName)) {
					map.put(timePlaceName, map.get(timePlaceName)+","+className);
				}else {
					map.put(timePlaceName, className);
				}
			}
		}
		
		for(Map.Entry<String, String> entry:map.entrySet()) {
			mainArrangementInfo+=entry.getValue()+"-"+entry.getKey();
		}
		if("".equals(mainArrangementInfo)) {
			mainArrangementInfo="暂无";
		}
		return mainArrangementInfo;
	}
	
	
	public String getSecondArrangement(Hfuu_Teacher teacher,String apiName) {
		List<Arrangement> secondArrangementList = teacher.getSecondArrangement();
		String secondArrangementInfo="";//副监考考务
		Map<String,String> map=new HashMap<>();//用于合并合班考试的班级信息
		String timePlaceName="";
		String className="";
		if(apiName.equals("wx")) {
			apiName="\n";
		}else {
			apiName="<br/>";
		}
		//处理副监考合班
		for(int i=0;i<secondArrangementList.size();i++) {
			className=secondArrangementList.get(i).getName();//班级名称
			if(apiName.equals("\n")) {
				String[] timeArr = secondArrangementList.get(i).getTime().split(":");
				timePlaceName=secondArrangementList.get(i).getSname()//课程名
						+"-----第"+timeArr[0]+"周-星期"+timeArr[1]+"-第"+timeArr[2]+"节----"//考试时间
						+"-"+secondArrangementList.get(i).getPlace()+" "+apiName;//考试地点
			}
			if(apiName.equals("<br/>")) {
				String timeArr=secondArrangementList.get(i).getTime();
				timePlaceName=secondArrangementList.get(i).getSname()//课程名
						+timeArr//考试时间
						+"-"+secondArrangementList.get(i).getPlace()+" "+apiName;//考试地点
			}
			if(map.isEmpty()) {
				map.put(timePlaceName, className);
			}else {
				if(map.containsKey(timePlaceName)) {
					map.put(timePlaceName, map.get(timePlaceName)+","+className);
				}else {
					map.put(timePlaceName, className);
				}
			}
		}
		for(Map.Entry<String, String> entry:map.entrySet()) {
			secondArrangementInfo+=entry.getValue()+"-"+entry.getKey();
		}
		if("".equals(secondArrangementInfo)) {
			secondArrangementInfo="暂无";
		}
		return secondArrangementInfo;
	}
	
	/**
	 * 发送带个邮件
	* @Title: sendSingleMail
	* @Description: 
	* @param @param teacher    参数
	* @return void    返回类型
	* @throws
	 */
	public void sendSingleMail(Hfuu_Teacher teacher,User user) {
		String id = teacher.getId();//教师编号
		String mail=getTeacherMailInfoById(id);//教师邮箱
		String name=teacher.getName();//教师姓名
		String mainArrangementInfo=getMainArrangement(teacher,"mail");//主监考考务
		String secondArrangementInfo=getSecondArrangement(teacher,"mail");//副监考考务
		
		String textInfo="<h2>"+name+"老师您好，您本次的监考信息如下：</h2>"
				+ "<table border='1'><tr><th>主监考考务</th><th>副监考考务</th></tr><tr><td><h3>"
				+mainArrangementInfo+"</h3></td><td><h3>"
				+secondArrangementInfo+"</h3></td></tr></table>";

		
		//发送邮件
		Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com"); //// 设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf=null;
		try {
			sf = new MailSSLSocketFactory();sf.setTrustAllHosts(true);
	        prop.put("mail.smtp.ssl.enable", "true");
	        prop.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        //使用JavaMail发送邮件的5个步骤
        //创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
            	/**
            	 * 整合后将此处改为管理员的emai和smtp
            	 */
                return new PasswordAuthentication(user.getEmail(), user.getSmtp());
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        Transport ts=null;
        try {
        	//2、通过session得到transport对象
        	ts = session.getTransport();
        	//3、使用邮箱的用户名和授权码连上邮件服务器
        	/**
        	 * 整合后将此处改为管理员的emai和smtp
        	 */
			ts.connect("smtp.qq.com", user.getEmail(), user.getSmtp());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        //4、创建邮件
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        try {
        	//指明邮件的发件人
        	/**
        	 * 整合后将此处改为管理员的email
        	 */
			message.setFrom(new InternetAddress(user.getEmail()));
			
			//指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
			/**
			 * 此处改为教师的mail
			 */
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));

	        //邮件的标题
	        message.setSubject("合肥学院考务信息通知");

	        //邮件的文本内容
	        message.setContent(textInfo, "text/html;charset=UTF-8");

	        //5、发送邮件
	        ts.sendMessage(message, message.getAllRecipients());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        if(ts!=null) {
        	try {
				ts.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
        }
	}
	
	public void sendAllMail(List<Hfuu_Teacher> teacherList,User user) {
		for(Hfuu_Teacher teacher:teacherList) {
			if(teacher.getMainArrangement().size()!=0||teacher.getSecondArrangement().size()!=0) {
				sendSingleMail(teacher,user);
			}
		}
	}
	
}
