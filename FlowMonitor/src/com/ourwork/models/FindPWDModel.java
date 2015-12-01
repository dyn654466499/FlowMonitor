package com.ourwork.models;

import static com.ourwork.controlers.ControlerState.MODEL_GET_VCODE;
import static com.ourwork.controlers.ControlerState.MODEL_SURE_REGISTER;
import static com.ourwork.controlers.ControlerState.VIEW_VCODE_CHANGE;

import java.util.Timer;
import java.util.TimerTask;

import com.ourwork.views.FindPWDView;
import com.ourwork.views.RegisterView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * “找回密码”view对应的model
 * @author 邓耀宁
 *
 */
public class FindPWDModel extends Models{

	/**
	 * 设置相应的handler
	 * @param handler
	 */
	public FindPWDModel(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		switch (changeState) {
		case MODEL_GET_VCODE:
			new Timer().schedule(new TimerTask() {
				//控制60秒
				int count = 60;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message.obtain(handler, VIEW_VCODE_CHANGE, count, 0,
							null).sendToTarget();
					//Activity结束时取消线程运作
					if (count == 0 || FindPWDView.isActivityfinish()) {
						cancel();
					} else {
						count--;
					}
				}
			}, 0, 1000);
			break;
		default:
			break;
		}
	}

	@Override
	public void changeModelState(Message changeStateMessage) {
		// TODO Auto-generated method stub
		
	}

}
