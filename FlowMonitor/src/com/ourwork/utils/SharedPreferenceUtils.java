package com.ourwork.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
	
	public static void setSearchHistory(Context context ,String searchKey){
		SharedPreferences preferences = context.getSharedPreferences("searchKey", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();  
		String history = preferences.getString("searchKey", "");  
		StringBuilder sb = new StringBuilder(history);  
		String appendString = searchKey + ",";
		if(history.contains(appendString)) {  
			int start = sb.indexOf(appendString);
			int end = start + appendString.length();
			sb.delete(start,end);
		} 
		sb.append(appendString);
		editor.putString("searchKey", sb.toString());
	    editor.commit(); 
	}
	
	public static ArrayList<String> getSearchHistory(Context context){
		SharedPreferences preferences = context.getSharedPreferences("searchKey", Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();  
		String searchKey = preferences.getString("searchKey", "");  
		if(searchKey == null || "".equals(searchKey)){
			return null;
		}
		ArrayList<String> searchKeys = new ArrayList<String>();
		String[] temp = searchKey.split(",");
		for(int i = temp.length;i>0;i--){
			searchKeys.add(temp[i-1]);
		}
		editor.commit();
		return searchKeys;
	}
	
	public static void clearSearchHistory(Context context){
		SharedPreferences preferences = context.getSharedPreferences("searchKey", Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();  
		editor.clear();  
        editor.commit();  
	}
	
}
