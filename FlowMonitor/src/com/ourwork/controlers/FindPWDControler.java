package com.ourwork.controlers;

import com.ourwork.models.FindPWDModel;

import android.app.Activity;
/**
 * “找回密码”view的controler
 * @author 邓耀宁
 *
 */
public class FindPWDControler extends Controlers {

	/**
	 * 设置controler的model
	 * @param activity
	 */
	public FindPWDControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new FindPWDModel(handler);
	}

}
