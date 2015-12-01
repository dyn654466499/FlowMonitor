package com.ourwork.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import com.ourwork.flowmonitor.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * 公共的工具类
 * @author 邓耀宁
 *
 */
public class CommonUtils {
	/** 
	 * 验证手机格式 
	 * @return 如果符合手机格式，返回true；否则返回false
	 */  
	public static boolean isPhoneNum(String phoneNumber) {  
	    /* 
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 
	    联通：130、131、132、152、155、156、185、186 
	    电信：133、153、180、189、（1349卫通） 
	    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 
	    */  
	    String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
	    if (TextUtils.isEmpty(phoneNumber)) return false;  
	    else return phoneNumber.matches(telRegex);  
	   } 
	
	/**
	 * 获取手机号码对应的国家代码
	 * @param mActivity
	 * @return 国家代码
	 */
	public static String GetCountryZipCode(Activity mActivity) {
		String CountryID = "";
		String CountryZipCode = "";
		TelephonyManager manager = (TelephonyManager) mActivity
				.getSystemService(Context.TELEPHONY_SERVICE);
		// getNetworkCountryIso
		CountryID = manager.getSimCountryIso().toUpperCase(Locale.getDefault());
		String[] rl = mActivity.getResources().getStringArray(
				R.array.CountryCodes);
		for (int i = 0; i < rl.length; i++) {
			String[] g = rl[i].split(",");
			if (g[1].trim().equals(CountryID.trim())) {
				CountryZipCode = g[0];
				break;
			}
		}
		return CountryZipCode;
	}
	
	/**
	 * 自定义middle()方法：取出语义理解返回的文本结果中的特定结果
	 * 
	 * @param input
	 *            （字符串结果）,index（ 截取开始的字符串位置，以1开始）,endPos（截取结束的字符串位置）
	 */
	public static String middle(String input, int index, int count) {
		if (input.isEmpty()) {
			return "";
		}
		count = (count > input.length() - index + 1) ? input.length()
				- index + 1 : count;
		return input.substring(index - 1, index + count - 1);
	}

	/**
	 * 判断是否text中是否含有小写字母
	 * @param text
	 * @return
	 */
	public static boolean isHaveLowerCase(String text) {
		return Pattern.compile("(?i)[a-z]").matcher(text).find();
	}
	
	/**
	 * 设置字母为大写
	 * @param text
	 * @return
	 */
	public static String setUpperCase(String text) {
		if (isHaveLowerCase(text)) {
			
			text = text.toUpperCase(Locale.getDefault());
		}
		return text;
	}

	/**
	 * 格式化时间
	 * @param sSec
	 * @return
	 */
	public static String formatDuration(String sSec){
		  String dur = null;
		  if(sSec!=null)
		  {		
			double time=Double.parseDouble(sSec);
			int second=(int)time%60;	
			int minute=(int)time/60%60;
			int hour=(int)time/3600;	
			String s= (second<10)?"0"+String.valueOf(second):String.valueOf(second);
			String m=(minute<10)?"0"+String.valueOf(minute):String.valueOf(minute);
			String h=(hour<10)?"0"+String.valueOf(hour):String.valueOf(hour);
			dur=h+":"+m+":"+s;										
		  }
		    return dur;
	}
	
	public static String formatTime(int what){
		 String dur = null;
		 dur = (what<10)?"0"+String.valueOf(what):String.valueOf(what);
		 return dur;
	}
	
	/**
	 * 判断手机是否连接网络
	 * @param context
	 * @return
	 */
    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
    /** 
     * 格式化字节数
     * @param size 
     * @return 格式化后的数据
     */  
    public static String getFormatTrafficSize(long size) {  
    	long kiloByte = size / 1024;  
        if (kiloByte < 1) {  
        	return "没有使用流量";
        }  
  
        long megaByte = kiloByte / 1024;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "KB";  
        }  
  
        long gigaByte = megaByte / 1024;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "MB";  
        }  
  
        long teraBytes = gigaByte / 1024;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "GB";  
        }  
        
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
                + "TB";  
    }
    
    /**
     * 读取文件数据  
     * @param fileName
     * @return
     * @throws IOException
     */
    public String readFileData(String fileName) throws IOException{   
      String res="";   
      try{   
    	     File file = new File(fileName);
             FileInputStream fis = new FileInputStream(file);   
             int length = fis.available();   
             byte [] buffer = new byte[length];   
             fis.read(buffer);       
             res = EncodingUtils.getString(buffer, "UTF-8");   
             fis.close();       
         }   
         catch(Exception e){   
             e.printStackTrace();   
         }   
         return res;   
    }  
    
    /**
     * 格式化手机号码(135*****7710)
     * @param phoneNum
     * @return 
     */
    public static String formatPhoneNum(String phoneNum){
        String target = phoneNum.substring(3, 7);
        phoneNum = phoneNum.replace(target, "*****");
    	return phoneNum;
    }
    
    /**
     * 根据参数改变指定文本的部分字体大小，颜色。
     * @param text 需要处理的整体文本
     * @param start 指定文本的开始位置
     * @param end 指定文本的结束位置
     * @param color 字体颜色
     * @param size 字体大小
     * @return 处理后的文本
     */
    public static SpannableString setFontType(String text, int start, int end, int color, int size)
	  {
	    SpannableString spannableString = new SpannableString(text);
		spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(size,true), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
	    return spannableString;
	  }
}
