package com.vivebest.mall.core.util;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 正则表达式工具类
 * 
 * @author mo.xf
 *
 */
public class RegexUtils {
	
	
	//验证文件后缀
//	Pattern p = Pattern.compile(".+\\.js$",Pattern.CASE_INSENSITIVE);//不区分大小写的,默认只有一个参数时是区分大小写
//	System.out.println(p.matcher("dads.jS").matches());

	/**
	 * 对一字符串进行正则验证
	 * 验证一个字符串中长度必须大于6位，同时必须包含至少一个字符和一个数值 
	 * 
	 * @param str 待验证的字符串
	 * 
	 * @return 
	 * 		通过验证时返回null
	 * 		如果验证出现问题，会返回对应的失败原因
	 * @throws Exception 
	 */
	public static String validateString(String str) throws Exception{
		//验证长度
		if(validateLength(str)==null){
			//验证字符元素
			return isContainNumber(str);
		}else{
			return validateLength(str);
		}
		
	}
	
	/**
	 * 验证content中必须同时存在数字和字母
	 * @param content
	 * @return
	 * 			通过验证时，返回null  否则返回对应提示
	 * @throws Exception 
	 * 
	 */
	public static String isContainNumber(String content) throws Exception{
		String message = "必须包含数字和字母";
		String restring = "^.*(([a-zA-Z]+.*[0-9]+)|([0-9]+.*[a-zA-Z]+)).*$";  //必须六位以上,包含数字和字母
		Pattern pattern=Pattern.compile(restring);
        if (pattern.matcher(content).matches()) { 
            return null;
        } 
		return message;
	}
	
	/**
	 * 验证content的长度必须大于等六位
	 * @param content
	 * @return
	 * 			通过验证时，返回null  否则返回对应提示
	 * @throws Exception 
	 */
	public static String validateLength(String content) throws Exception{
		String message = "必须六位以上";
		String restring = "^.{6,}$";
		Pattern pattern=Pattern.compile(restring);
        if (pattern.matcher(content).matches()) { 
            return null;
        } 
		return message;
	}
	
	
	/**
	 * 如果字符串url是否以指定的数组的任一字符串结尾
	 * @param url
	 * 			指定字符串
	 * @param noFomat
	 * 			格式集[".js",".html"] <<主要是用于文件格式的验证，必须以"."开头加后缀名    如： .js >>
	 * @return
	 * 			url不是以指定的数组的任一字符串结尾  返回  true 
	 * 			否则返回false
	 */
	public static boolean validateUrl(String url,String[] noFomat){
		//正则非
		String endStr = "";
		//为空时返回true
		if(noFomat==null || noFomat.length-0 == 0 || url==null){
			return true;
		}else{
			for(int i=0; i<noFomat.length; i++ ){
				if(i-0 == 0){
					endStr = "((\\"+noFomat[i]+")";
				}else{
					endStr = endStr+"|(\\"+noFomat[i]+")";
				}
				
				if(i-noFomat.length+1 == 0){
					endStr = endStr+")$";
				}
			}
		}
		//定义正则
		String reg = "^.*";
		//验证文件后缀
		//其中参数意义：不区分大小写的,默认只有一个参数时是区分大小写
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(reg+endStr,java.util.regex.Pattern.CASE_INSENSITIVE);
		return !p.matcher(url).matches();
	}
	
	
	/**
	 * 验证指定的字符串是不是符点型数值
	 * 
	 * @param value
	 * 			某指定的字符串
	 * @return
	 * 			字符串符合数值格式时返回true,否则返回false
	 */
	public static boolean isFloat(String value){
		String restring = "^-?(\\d)+((.\\d+)|(\\d)*)$";
		Pattern pattern=Pattern.compile(restring);
        if (pattern.matcher(value).matches()) { 
            return true;
        }
        return false;
	}
	
	/**
	 * 
	 * 原始字符串与正则表达式匹配的部分进行替换
	 * 
	 * @param str  原始字符串
	 * @param regex  正则表达式
	 * @param source 
	 * @param dest  
	 * @return
	 */
	public static String replacePart(String str , String regex , String source,String dest){
		Pattern pt = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		  Matcher matcher = pt.matcher(str);
		  while(matcher.find())
		  {
			 String group = matcher.group() ;
			 String t1=group.replace(source, dest);
			 str = str.replace(group,t1);
		  }
		  return str;
	}
	
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    } 
    
    /** 
     * 是否银行卡 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isBankCardNo(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^\\d{10,30}$"); 
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  

	 
    /**
	 * 检查整数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean checkNumber(String num)
	{
		String eL = "^\\d+$";// 非负整数
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}
	
	 /**
		 * 检查浮点数
		 * 
		 * @param num
		 * @return
		 */
	public static boolean checkFloat(String num){
		String eL =  "^\\d+(\\.\\d+)?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}
	
	public static void main(String[] args) {
		
		//String str = "验证码：@{cod2d_e1},易提基金欢迎@{sdfsdf@{sdfsdf}您的加入，请于120秒内输入，请勿泄露。";
		
		//System.out.println(replacePart(str ,"@\\{\\s*[a-zA-Z]{1}\\w*\\s*\\}" , "@" , "$")) ;
		
		//System.out.println(RegexUtils.isBankCardNo("22222 223322"));
		System.out.println("222 22 223 322".replaceAll("\\p{Blank}", ""));
		
	}
}
