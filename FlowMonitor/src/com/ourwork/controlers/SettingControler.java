package com.ourwork.controlers;

import com.ourwork.models.SettingModel;

import android.app.Activity;

/**
 * “更多设置”view的controler
 * @author 邓耀宁
 *
 */
public class SettingControler extends Controlers{
	/**
	 * 设置controler的model
	 * @param activity
	 */
	public SettingControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new SettingModel(handler);
		
	}

}
