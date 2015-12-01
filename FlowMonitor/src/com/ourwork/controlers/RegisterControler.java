package com.ourwork.controlers;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.ourwork.models.RegisterModel;

/**
 * “注册”view的controler
 * @author 邓耀宁
 *
 */
public class RegisterControler extends Controlers{

	/**
	 * 设置controler的model
	 * @param activity
	 */
	public RegisterControler(Activity activity) {
		super(activity);
		modelDelegate = new RegisterModel(handler);
	}}
