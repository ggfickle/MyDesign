package hfuu.examination.test;


import java.util.ArrayList;
import java.util.function.Predicate;

public class Test1 {
	//过滤；满足条件为女 姓名4个字
	public static ArrayList<String> filter(String[] arr,Predicate<String> pre1,Predicate<String> pre2) {
		ArrayList<String> list = new ArrayList<>();
		for (String s : arr) {
			boolean b = pre1.and(pre2).test(s);
			if (b) {
				list.add(s);
				
			}
			
		}
		return list;
	}
	
	public static void main(String[] args) {
		String[] arr = {"迈克乔丹,男","迪丽热巴,女","芙拉莉扎,女","詹姆斯,男","马卡,男"};
		ArrayList<String> list = filter(arr, (String s)->{
			return s.split(",")[1].equals("女");
		}, (String  s)->{
			return s.split(",")[0].length()==4;
		});
		
		for (String s : list) {
			System.out.println(s);
		}
	}
}